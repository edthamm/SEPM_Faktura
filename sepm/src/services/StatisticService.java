package services;

import java.util.List;

import entities.Product;

public interface StatisticService {
	public List<Product> getTopThreeProductsOfLastThirtyDays();
}
