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


package org.hsqldb.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;

/* $Id: JDBCSavepoint.java 3481 2010-02-26 18:05:06Z fredt $ */

// Revision 1.10  2006/07/12 12:38:22  boucherb
// - full synch up to Mustang b90

/**
 * The representation of a savepoint, which is a point within
 * the current transaction that can be referenced from the
 * <code>Connection.rollback</code> method. When a transaction
 * is rolled back to a savepoint all changes made after that
 * savepoint are undone.
 * <p>
 * Savepoints can be either named or unnamed. Unnamed savepoints
 * are identified by an ID generated by the underlying data source.
 *
 * <!-- start Release-specific documentation -->
 * <div class="ReleaseSpecificDocumentation">
 * <h3>HSQLDB-Specific Information:</h3> <p>
 *
 * SQL 2003 standard does not support unnamed savepoints. However, this
 * feature is supported from version 2.0.<p>
 *
 * If the connection is autoCommit, setting savepoints has no effect as any
 * such savepoint is cleared upon the execution of the first transactional
 * statement. <p>
 *
 * </div>
 * <!-- end release-specific documentation -->
 *
 *
 * @author boucherb@users
 * @since JDK 1.4, HSQLDB 1.7.2
 */
public class JDBCSavepoint implements Savepoint {

    int            id;
    String         name;
    JDBCConnection connection;

    JDBCSavepoint(String name, JDBCConnection conn) throws SQLException {

        if (name == null) {
            throw Util.nullArgument("name");
        }

        if (conn == null) {
            throw Util.nullArgument("conn");
        }
        this.name       = name;
        this.connection = conn;
    }

    JDBCSavepoint(JDBCConnection conn) throws SQLException {

        if (conn == null) {
            throw Util.nullArgument("conn");
        }
        this.id         = conn.getSavepointID();
        this.connection = conn;
    }

    /**
     * Retrieves the generated ID for the savepoint that this
     * <code>Savepoint</code> object represents.
     * @return the numeric ID of this savepoint
     * @exception SQLException if this is a named savepoint
     * @since 1.4
     */
    public int getSavepointId() throws SQLException {

        if (name == null) {
            return id;
        }

        throw Util.notSupported();
    }

    /**
     * Retrieves the name of the savepoint that this <code>Savepoint</code>
     * object represents.
     *
     * @return the name of this savepoint
     * @exception SQLException if this is an un-named savepoint
     * @since 1.4
     */
    public String getSavepointName() throws SQLException {

        if (name == null) {
            throw Util.notSupported();
        }

        return name;
    }

    public String toString() {
        return super.toString() + "[name=" + name + "]";
    }
}
