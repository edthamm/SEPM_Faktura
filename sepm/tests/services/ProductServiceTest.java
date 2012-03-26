package services;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductServiceTest {

	private ProductServiceImpl ps = new ProductServiceImpl(null);
	
	@Test
	public void testRoundTwoDecimalsUp() {
		assertTrue(ps.roundTwoDecimals(3.2557) == 3.26);
	}
	@Test
	public void testRoundTwoDecimalsDown() {
		assertTrue(ps.roundTwoDecimals(3.2547) == 3.25);
	}

}
