package services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entities.Consumption;

public class StatisticServiceTest {
	
	private StatisticServiceImpl stats = new StatisticServiceImpl(null,null);
	private List<Consumption> cons;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	  	PropertyConfigurator.configure("tests/log4jtest.properties");
	}
	
	@Before
	public void setUp() throws Exception {
		cons = new LinkedList<Consumption>();
		cons.add(new Consumption(1, 15, 0));
		cons.add(new Consumption(3, 5, 0));
		cons.add(new Consumption(2, 10, 0));
		cons.add(new Consumption(1, 1, 0));
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testCompress(){
		HashMap<Integer, Consumption> result = stats.compress(cons);
		
		assertTrue(result.get(1).getQuantity() == 16);
	}
	
	
	@Test
	public void testOrderListConsumptions() {
		
		List<Consumption> ordered = stats.orderListConsumtptions(cons);
		
		assertTrue(ordered.get(0).getProductID() == 1);
		assertTrue(ordered.get(1).getProductID() == 2);
		assertTrue(ordered.get(2).getProductID() == 3);
		
	}

}
