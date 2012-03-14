/* Copyright (c) 2001-2010, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.hsqldb;

import org.hsqldb.error.Error;
import org.hsqldb.error.ErrorCode;
import org.hsqldb.lib.OrderedHashSet;
import org.hsqldb.store.ValuePool;
import org.hsqldb.result.Result;

/**
 * Implementation of SQL TRIGGER objects.<p>
 *
 * @author Fred Toussi (fredt@users dot sourceforge.net)
 * @version 1.9.0
 * @since 1.9.0
 */
public class TriggerDefSQL extends TriggerDef {

    OrderedHashSet references;

    public TriggerDefSQL(HsqlNameManager.HsqlName name, int when,
                         int operation, boolean forEachRow, Table table,
                         Table[] transitions, RangeVariable[] rangeVars,
                         Expression condition, String conditionSQL,
                         int[] updateColumns, Routine routine) {

        super(name, when, operation, forEachRow, table, transitions,
              rangeVars, condition, conditionSQL, updateColumns);

        this.routine    = routine;
        this.references = routine.getReferences();
    }

    public OrderedHashSet getReferences() {
        return routine.getReferences();
    }

    public OrderedHashSet getComponents() {
        return null;
    }

    public void compile(Session session, SchemaObject parentObject) {}

    public String getClassName() {
        return null;
    }

    public boolean hasOldTable() {
        return transitions[OLD_TABLE] != null;
    }

    public boolean hasNewTable() {
        return transitions[NEW_TABLE] != null;
    }

    synchronized void pushPair(Session session, Object[] oldData,
                               Object[] newData) {

        Result result = Result.updateZeroResult;

        if (session.sessionContext.depth > 128) {
            throw Error.error(ErrorCode.GENERAL_ERROR);
        }

        session.sessionContext.push();

        if (transitions[OLD_ROW] != null) {
            rangeVars[OLD_ROW].getIterator(session).setCurrent(oldData);
        }

        if (transitions[NEW_ROW] != null) {
            rangeVars[NEW_ROW].getIterator(session).setCurrent(newData);
        }

        if (condition.testCondition(session)) {
            session.sessionContext.routineVariables =
                ValuePool.emptyObjectArray;
            result = routine.statement.execute(session);
        }

        session.sessionContext.pop();

        if (result.isError()) {
            throw result.getException();
        }
    }

    public String getSQL() {

        StringBuffer sb = getSQLMain();

        sb.append(routine.statement.getSQL());

        return sb.toString();
    }
}
