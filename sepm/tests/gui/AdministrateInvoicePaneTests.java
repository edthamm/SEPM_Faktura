package gui;

import gui.AdministrateInvoicePane.searchListener;

import org.junit.Test;


public class AdministrateInvoicePaneTests {

	private AdministrateInvoicePane  ip = new AdministrateInvoicePane(null);
	private searchListener sl = ip.getSearchListener();
	
	@Test
	public void testRegexSuccess() {
		sl.checkDateFormat("2009-12-01");
	}

	@Test(expected = IllegalArgumentException.class)
	public void check00DayDetect(){
		sl.checkDateFormat("1997-07-00");
	}
	@Test(expected = IllegalArgumentException.class)
	public void check00MonthDetect(){
		sl.checkDateFormat("2004-00-31");
	}
	@Test(expected = IllegalArgumentException.class)
	public void check00BothDetect(){
		sl.checkDateFormat("2009-00-00");
	}
	@Test(expected = IllegalArgumentException.class)
	public void checkDayOOB(){
		sl.checkDateFormat("1342-10-32");
	}
	@Test(expected = IllegalArgumentException.class)
	public void checkMonthOOB(){
		sl.checkDateFormat("1764-15-31");
	}
}
