package services;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import entities.Consumption;
import entities.Invoice;
import entities.Product;

/**
 * The Class StatisticServiceImpl.
 */
public class StatisticServiceImpl implements StatisticService{
	
	private Logger logger = Logger.getLogger("service.StatisticService.class");
	private InvoiceService is;
	private ProductService ps;
	
	/**
	 * Instantiates a new statistic service impl.
	 *
	 * @param ps the ProductServiceInstance
	 * @param is the InvoiceServiceInstance
	 */
	public StatisticServiceImpl(ProductService ps, InvoiceService is){
		this.is = is;
		this.ps = ps;
	}
	
	/* (non-Javadoc)
	 * @see services.StatisticService#getTopThreeProductsOfLastThirtyDays()
	 */
	@Override
	public List<Product> getTopThreeProductsOfLastThirtyDays() {
		logger.info("Entering get top sellers");
		List<Invoice> l = getInvoicesOfLast30Days();
		List<Consumption> c = extractConsumptions(l);
		List<Consumption> top3 = evaluateTop3(c);
		List<Product> result = transformConsumptionsToProducts(top3);
		return result;
	}
	
	private List<Invoice> getInvoicesOfLast30Days(){
		Date todayUtil = new Date();
		Calendar cal = new GregorianCalendar();
		cal.setTime(todayUtil);
		cal.add(Calendar.DAY_OF_MONTH, -30);
		Date ThirtyDaysAgoUtil = cal.getTime();
		java.sql.Date today = new java.sql.Date(todayUtil.getTime());
		java.sql.Date ThirtyDaysAgo = new java.sql.Date(ThirtyDaysAgoUtil.getTime());
		
		try {
			List<Invoice> result = is.getInvoicesByDates(ThirtyDaysAgo.toString(), today.toString());
			logger.debug("Got Invoicelist of last 30 Days with: "+result.size()+" elements");
			return result;
		} catch (IllegalArgumentException e) {
			logger.warn("Since these values are generated I should not run in to this "+e.toString());
		} catch (InvoiceServiceException e) {
			logger.warn("Since these values are generated I should not run in to this "+e.toString());
		}
		logger.debug("get invoices of last 30 returns null. this should not happen.");
		return null;
	}
	
	private List<Consumption> extractConsumptions(List<Invoice> l) {
		List<Consumption> result = new LinkedList<Consumption>();
		for(Invoice i : l){
			result.addAll(i.getConsumptions());
		}
		return result;
	}
	
	private List<Consumption> evaluateTop3(List<Consumption> c) {
		List<Consumption> compressed = compressList(c);
		List<Consumption> ordered = orderListConsumtptions(compressed);
		List<Consumption> result = selectTop3(ordered);
		return result;
	}
	
	private List<Consumption> compressList(List<Consumption> cList) {
		HashMap<Integer, Consumption> v = compress(cList);
	
		List<Consumption> result = buildList(v);
		
		return result;
	}

	protected HashMap<Integer, Consumption> compress(List<Consumption> cList) {
		HashMap<Integer, Consumption> m = new HashMap<Integer, Consumption>();

		for(Consumption c : cList){
			logger.debug("Processing "+ c.toString());
			if(m.containsKey(c.getProductID())){
				Consumption infoInMap = m.get(c.getProductID());
				logger.debug("Product already in list increasing count");
				int oldQuantity = infoInMap.getQuantity();
				int elementsQuantity = c.getQuantity();
				int newQuantity = oldQuantity + elementsQuantity;
				infoInMap.setQuantity(newQuantity);
			}
			else{
				m.put(c.getProductID(), c);
			}
		}
		return m;
	}
	private List<Consumption> buildList(HashMap<Integer, Consumption> m) {
		List<Consumption> result = new LinkedList<Consumption>();
		logger.info("Building list from compress");
		logger.debug("Original size "+m.size());
		Iterator<Entry<Integer, Consumption>> mEntries = m.entrySet().iterator();
		while(mEntries.hasNext()){
			Consumption c = mEntries.next().getValue();
			logger.debug("Adding to List in Build " +c.toString());
			result.add(c);
		}
		
		logger.debug("size after build "+ result.size());
		return result;
	}
	
	
	protected List<Consumption> orderListConsumtptions(
			List<Consumption> compressed) {
		logger.info("Ordering Sublist");
		Collections.sort(compressed, new ConsumptionComparer());
		logger.debug("Order after ordering:");
		for(Consumption c : compressed){
			logger.debug(c.toString());
		}
		return compressed;
	}
	
	private List<Consumption> selectTop3(List<Consumption> ordered) {
		logger.info("Getting Sublist");
		if(ordered.size() > 3){
			return ordered.subList(0, 3);
		}
		return ordered;
	}
	
	private List<Product> transformConsumptionsToProducts(List<Consumption> top3) {
		List<Product> result = new LinkedList<Product>();
		for(Consumption c : top3){
			result.add(ps.getProductbyId(c.getProductID()));
		}
		return result;
	}
	
	private class ConsumptionComparer implements Comparator<Consumption>{

		@Override
		public int compare(Consumption c0, Consumption c1) {
			int c0qty = c0.getQuantity();
			int c1qty = c1.getQuantity();
			if(c0qty > c1qty){
				return -1;
			}
			if(c1qty > c0qty){
				return 1;
			}
			return 0;
		}
		
	}

}
