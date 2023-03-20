package system;

import java.util.Collection;
import java.util.function.Consumer;

import datamodel.Customer;
import datamodel.Article;
import datamodel.Order;


/**
 * Interface to format and print objects and collections into a StringBuffer.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface Printer {

	/**
	 * Factory method to create TablePrinter instances.
	 * 
	 * @param sb StringBuffer to collect table content.
	 * @param builder initialize table at creation with columns, widths and alignment.
	 * @return TablePrinter instance.
	 */
	TablePrinter createTablePrinter(StringBuffer sb, Consumer<TablePrinter.Builder> builder);


	/**
	 * Print attributes of one Customer object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | 892474 | Meyer, Eric                    | eric98@yahoo.com, (030) 3945-642298          |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param customer Customer object.
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printCustomer(StringBuffer sb, Customer customer);


	/**
	 * Print collection of Customer objects into StringBuffer as lines with Customer attributes.
	 * <pre>
	 * Example:
	 * | 643270 | Bayer, Anne              | anne24@yahoo.de, (030) 3481-23352   |
	 * | 412396 | Blumenfeld, Nadine-Ulla  | +49 152-92454                       |
	 * | 892474 | Meyer, Eric              | eric98@yahoo.com, (030) 3945-642298 |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param customers collection of Customers (null argument is ignored).
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printCustomers(StringBuffer sb, Collection<Customer> customers);


	/**
	 * Print attributes of one Article object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | SKU-458362 | Tasse                      |    299 �|  19% MwSt|
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param article Article object
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printArticle(StringBuffer sb, Article article);


	/**
	 * Print collection of Article objects into StringBuffer as lines with Article attributes.
	 * <pre>
	 * Example:
	 * | SKU-458362 | Tasse                      |    299 �|  19% MwSt|
	 * | SKU-693856 | Becher                     |    149 �|  19% MwSt|
	 * | SKU-278530 | Buch "Java"                |   4990 �|   7% MwSt|
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param articles collection of Articles (null argument is ignored).
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printArticles(StringBuffer sb, Collection<Article> articles);


	/**
	 * Print attributes of one Order object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | 8592356245 | Meyer, Eric                | 4 items | created: 2022-05-16 08:16:42 |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param order Order object
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printOrder(StringBuffer sb, Order order);


	/**
	 * Print collection of Order objects into StringBuffer as lines with Order attributes.
	 * <pre>
	 * Example:
	 * | 8592356245 | Meyer, Eric                | 4 items | created: 2022-05-16 08:16:42 |
	 * | 3563561357 | Bayer, Anne                | 2 items | created: 2022-05-16 08:16:42 |
	 * | 5234968294 | Meyer, Eric                | 1 items | created: 2022-05-16 08:16:42 |
	 * | 6135735635 | Blumenfeld, Nadine-Ulla    | 3 items | created: 2022-05-16 08:16:42 |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param orders collection of Orders (null argument is ignored).
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printOrders(StringBuffer sb, Collection<Order> orders);


	/**
	 * Print order into TablePrinter with order item separated lines.
	 * <pre>
	 * Example:
	 * +----------+---------------------------------------------+--------------------+
	 * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
	 * +----------+---------------------------------------------+--------------------+
	 * |8592036245|Eric's Bestellung                            |                    |
	 * |          | - 4 Teller, 4x 6.49          4.14     25.96�|                    |
	 * |          | - 8 Becher, 8x 1.49          1.90     11.92�|                    |
	 * |          | - 1 Buch "OOP"               5.23*    79.95�|                    |
	 * |          | - 4 Tasse, 4x 2.99           1.91     11.96�|   13.18�    129.79�|
	 * +----------+---------------------------------------------+--------------------+
	 * </pre>
	 * 
	 * @param orderTable to print order into.
	 * @param order order printed into {@code orderTable}, (null argument is ignored).
	 * @return {@link TablePrinter} that contains formatted result.
	 */
	public TablePrinter printOrder(TablePrinter orderTable, Order order);


	/**
	 * Print collection of order objects into TablePrinter.
	 * <pre>
	 * Example:
	 * +----------+---------------------------------------------+--------------------+
	 * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
	 * +----------+---------------------------------------------+--------------------+
	 * |8592356245|Eric's Bestellung:                           |                    |
	 * |          | - 4 Teller, 4x 6.49          4.14     25.96�|                    |
	 * |          | - 8 Becher, 8x 1.49          1.90     11.92�|                    |
	 * |          | - 1 Buch "OOP"               5.23*    79.95�|                    |
	 * |          | - 4 Tasse, 4x 2.99           1.91     11.96�|   13.18�    129.79�|
	 * +----------+---------------------------------------------+--------------------+
	 * |3563561357|Anne's Bestellung:                           |                    |
	 * |          | - 2 Teller, 2x 6.49          2.07     12.98�|                    |
	 * |          | - 2 Tasse, 2x 2.99           0.95      5.98�|    3.02�     18.96�|
	 * +----------+---------------------------------------------+--------------------+
	 *  {@code >>>>>>>>>>}                                       Gesamt:|   16.20�    148.75�|
	 *                                                          +====================+
	 * </pre>
	 * 
	 * @param orderTable to print orders into.
	 * @param orders collection of orders printed into {@code orderTable}, (null argument is ignored).
	 * @return {@link TablePrinter} that contains formatted result.
	 */
	public TablePrinter printOrders(TablePrinter orderTable, Collection<Order> orders);

}
