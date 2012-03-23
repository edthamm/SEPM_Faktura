package services;

import java.util.List;

import entities.Product;

/**
 * The Interface StatisticService.
 */
public interface StatisticService {
	
	/**
	 * Gets the three top selling products of last thirty days.
	 *
	 * @return a list of the three top selling products of last thirty days
	 */
	public List<Product> getTopThreeProductsOfLastThirtyDays();
}
