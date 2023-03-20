package system;

import java.util.*;

//import application.package_info;
import datamodel.Customer;
import datamodel.Article;
import datamodel.Order;

/**
 * Factory that creates instances of objects of the {@link datamodel} package.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface DatamodelFactory {

	
	/**
	 * Customer factory method using default constructor.
	 * 
	 * @return Customer object created with default constructor.
	 */
	public Customer createCustomer();


	/**
	 * Customer factory method using constructor with name argument.
	 * 
	 * @param name single-String Customer name, e.g. "Eric Meyer".
	 * @return Customer object created with constructor with name argument.
	 */
	public Customer createCustomer(String name);


	/**
	 * Article factory method using default constructor.
	 * 
	 * @return Article object created with default constructor.
	 */
	public Article createArticle();


	/**
	 * Article factory method using constructor with description and unitPrice arguments.
	 * 
	 * @param description descriptive text for article.
	 * @param unitPrice price (in cent) for one unit of the article.
	 * @return Article object created with constructor with description and unitPrice arguments.
	 */
	public Article createArticle(String description, long unitPrice);
	


	/**
	 * Order factory method using constructor with owning customer as argument.
	 * 
	 * @param customer owning customer who created the order.
	 * @return Order object created with constructor with owning customer as argument.
	 * @throws IllegalArgumentException when customer argument is null or has invalid id.
	 */
	public Order createOrder(Customer customer);


	/**
	 * Getter method to return created Customer objects.
	 * 
	 * @return created Customer objects.
	 */
	public List<Customer> getCustomers();


	/**
	 * Getter method to return created Article objects.
	 * 
	 * @return created Article objects.
	 */
	public List<Article> getArticles();


	/**
	 * Getter method to return created Order objects.
	 * 
	 * @return created Order objects.
	 */
	public List<Order> getOrders();


	/**
	 * Return number of created Customer objects.
	 * 
	 * @return number of created Customer objects.
	 */
	public int customersCount();


	/**
	 * Return number of created Article objects.
	 * 
	 * @return number of created Article objects.
	 */
	public int articlesCount();


	/**
	 * Find a created Customer object by its id.
	 * 
	 * @param id customer id.
	 * @return Optional with found object or empty Optional.
	 */
	public Optional<Customer> findCustomerById(long id);


	/**
	 * Find a created Article object by its id.
	 * 
	 * @param id article id.
	 * @return Optional with found object or empty Optional.
	 */
	public Optional<Article> findArticleById(String id);


	/**
	 * Find a created Order object by its id.
	 * 
	 * @param id order id.
	 * @return Optional with found object or empty Optional.
	 */
	public Optional<Order> findOrderById(String id);


	/**
	 * Return number of created Order objects.
	 * 
	 * @return number of created Order objects.
	 */
	public int ordersCount();



}
