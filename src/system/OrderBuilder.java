package system;

//import application.package_info;



/**
 * OrderBuilder builds sample orders using objects from the {@link datamodel} package.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface OrderBuilder {


	/**
	 * Method to build a first set of Customer, Article and Order objects.
	 * 
	 * @return chainable self-reference.
	 */
	public OrderBuilder buildOrders();


	/**
	 * Method to build another set of Customer, Article and Order objects.
	 * 
	 * @return chainable self-reference.
	 */
	public OrderBuilder buildMoreOrders();
}

