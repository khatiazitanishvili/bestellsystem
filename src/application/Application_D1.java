package application;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import datamodel.Article;
import datamodel.Customer;
import datamodel.Order;
import datamodel.TAX;


/**
 * Runnable application class that creates and outputs simple orders using the
 * {@link datamodel}.
 * <code>
 * <a href="{@docRoot}/index.html">{@value application.package_info#RootName}</a>.
 * </code>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Application_D1 {
	//
	private Customer eric	= null;
	private Customer anne	= null;
	private Customer tim	= null;
	private Customer nadine	= null;
	private Customer khaled	= null;
	//
	private Article tasse	= null;
	private Article becher	= null;
	private Article kanne	= null;
	private Article teller	= null;
	private Article buch_Java = null;
	private Article buch_OOP= null;
	//
	private Order o8592 = null;		// Eric's 1st order
	private Order o3563 = null;		// Anne's order
	private Order o5234 = null;		// Eric's 2nd order
	private Order o6135 = null;		// Nadine's order

	/**
	 * list with Customers, List.of(eric, anne, tim, nadine, khaled)
	 */
	private final List<Customer> customers;

	/**
	 * list with Articles, List.of(aTasse, aBecher, aKanne, aTeller)
	 */
	private final List<Article> articles;

	/**
	 * list with Orders, List.of(o5234,  o8592,  o3563,  o6135)
	 */
	private final List<Order> orders;


	/**
	 * Private constructor to initialize local attributes.
	 */
	private Application_D1() {
		System.out.println("se1.Bestellsystem" + ": " + this.getClass().getSimpleName());
		//
		this.customers = buildCustomers();
		this.articles = buildArticles();
		this.orders = buildOrders();
	}


	/**
	 * Public main() function.
	 * 
	 * @param args arguments passed from command line.
	 */
	public static void main(String[] args) {
		var appInstance = new Application_D1();
		appInstance.run();
	}


	/**
	 * Private method that runs with application instance.
	 */
	private void run() {
		// 
		StringBuffer sb = new StringBuffer();
		//
		printCustomers(sb.append("\nCustomers:\n"), customers);
		//
//		print(sb.append("\nCustomers_sorted by name:\n"), customers,
//			stream -> stream.sorted((c1, c2) -> c1.getLastName().compareTo(c2.getLastName())),
//			c -> printCustomer(sb, c));
		//
		printArticles(sb.append("\nArticles:\n"), articles);
		//
//		print(sb.append("\nArticles_sorted by price:\n"), articles,
//			stream -> stream.sorted((a1, a2) -> Long.compare(a1.getUnitPrice(), a2.getUnitPrice())),
//			a -> printArticle(sb, a));
		//
		printOrders(sb.append("\nOrders:\n"), orders);
		//
		System.out.println(sb);
	}


	/**
	 * Private builder method for Customer objects.
	 * 
	 * @return collection of created Customer objects.
	 */
	private List<Customer> buildCustomers() {
		eric = new Customer("Eric Meyer")
			.setId(892474L)
			.addContact("eric98@yahoo.com")
			.addContact("(030) 3945-642298");
		//
		anne = new Customer("Bayer, Anne")
			.setId(643270L)
			.addContact("anne24@yahoo.de")
			.addContact("(030) 3481-23352")
			.addContact("fax: (030)23451356");
		//
		tim = new Customer("Tim Schulz-Mueller")
			.setId(286516L)
			.addContact("tim2346@gmx.de");
		//
		nadine = new Customer("Nadine-Ulla Blumenfeld")
			.setId(412396L)
			.addContact("+49 152-92454");
		//
		khaled = new Customer()
			.setName("Khaled Saad Mohamed Abdelalim")
			.setId(456454L)
			.addContact("+49 1524-12948210");
		//
		// return new ArrayList<Customer>(List.of(eric, anne, tim, nadine, khaled));
		var result = toList(eric, anne, tim, nadine, khaled);
		System.out.println("(" + result.size() + ") Customer objects built.");
		return result;
	}


	/**
	 * Private builder method for Article objects.
	 * 
	 * @return collection of created Article objects.
	 */
	private List<Article> buildArticles() {
/*
 * TODO: uncomment articles
 */
	
		tasse  = new Article("Tasse",  299)
		        .setId("SKU-458362");
		becher = new Article("Becher", 149)
				.setId("SKU-693856");
		kanne  = new Article("Kanne", 1999)
				.setId("SKU-518957");
		
		teller = new Article("Teller", 649)
				.setId("SKU-638035");
		//
		buch_Java = new Article("Buch \"Java\"", 4990)
				.setId("SKU-278530")
				.setTax(TAX.GER_VAT_REDUCED);	// reduced tax rate for books
		//
		buch_OOP = new Article("Buch \"OOP\"", 7995)
				.setId("SKU-425378")
				.setTax(TAX.GER_VAT_REDUCED);	// reduced tax rate for books
		//
		var result = toList(tasse, becher, kanne, teller, buch_Java, buch_OOP);
		System.out.println("(" + result.size() + ") Article objects built.");
		return result;
	}


	/**
	 * Private builder method for Order objects.
	 * 
	 * @return collection of created Order objects.
	 */
	private List<Order> buildOrders() {
/*
 * TODO: uncomment orders
 */
		// Eric's 1st order
		o8592 = new Order(eric)	// new order for Eric
				.setId("8592356245")	// assign order-id: 8592356245
				// add items to order
				.addItem(teller, 4)		// 4 Teller, 4x 6.49 �
				.addItem(becher, 8)		// 8 Becher, 8x 1.49 �
				.addItem(buch_OOP, 1)	// 1 Buch "OOP", 1x 79.95 �, 7% MwSt (5.23�)
				.addItem(tasse, 4);		// 4 Tassen, 4x 2.99 �
		
		//
		// Anne's order
		o3563 = new Order(anne)
				.setId("3563561357")
				.addItem(teller, 2)
				.addItem(tasse, 2);
		//
		// Eric's 2nd order
		o5234 = new Order(eric)
				.setId("5234968294")
				.addItem(kanne, 1);
		//
		// Nadine's order
		o6135 = new Order(nadine)
				.setId("6135735635")
				.addItem(teller, 12)
				.addItem(buch_Java, 1)
				.addItem(buch_OOP, 1);
		//
		var result = toList(o8592, o3563, o5234, o6135);
		System.out.println("(" + result.size() + ") Order objects built.");
		return result;
	}


	/**
	 * Convert variable args of elements of a generic type {@code <T>} to {@code List<T>}.
	 * <i>null</i> arguments are removed from the resulting list.
	 * <p> 
	 * It is a substitute for {@code List.of(args)}, which does not allow <i>null</i>
	 * elements.
	 * </p>
	 * 
	 * @param <T> generic element type
	 * @param args variable argument list of type {@code <T>}.
	 * @return list of elements of type {@code <T>} with <i>null</i> arguments removed.
	 */
	@SafeVarargs
	private <T> List<T> toList(T... args) {
		return Arrays.asList(args).stream()
			.filter(c -> c != null)	// remove null arguments from resulting list
			.collect(Collectors.toList());
	}


	/**
	 * Generic print method for collection of elements of type T that offers a stream callout
	 * before print to filter, sort, map the stream before each element is printed by invoking
	 * the printEach functional interface.
	 * <pre>
	 * Example of Customer list sorted by name:
	 * | 643270 | Bayer, Anne              | anne24@yahoo.de, (030) 3481-23352   |
	 * | 412396 | Blumenfeld, Nadine-Ulla  | +49 152-92454                       |
	 * | 892474 | Meyer, Eric              | eric98@yahoo.com, (030) 3945-642298 |
	 * </pre>
	 * 
	 * @param <T> generic element type of stream to be printed.
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param collection elements to be streamed and printed.
	 * @param stream callout to filter, sort, etc. {@code Stream<T>} before each element {@code t} is printed.
	 * @param printEach functional interface to print each element {@code t} into StringBuffer.
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public <T> StringBuffer print(StringBuffer sb, final Collection<T> collection,
			final Function<Stream<T>, Stream<T>> stream,
			final Consumer<T> printEach
	) {
		if(collection != null) {
			(stream != null? stream.apply(collection.stream()) : collection.stream())
				.forEach(t -> printEach.accept(t));
		}
		return sb;
	}


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
	public StringBuffer printCustomers(StringBuffer sb, final Collection<Customer> customers) {
		if(customers==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
//		var cit = customers.iterator();
//		while(cit.hasNext()) {		// iterate through Customer collection
//			Customer c = cit.next();
//			printCustomer(sb, c);		// format each Customer object as line into StringBuffer
//		}
//		return sb;
		return print(sb_, customers, s -> s, c -> printCustomer(sb_, c));	// calling generic print method
	}


	/**
	 * Print attributes of one Customer object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | 892474 | Meyer, Eric                    | eric98@yahoo.com, (030) 3945-642298          |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param c Customer object.
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printCustomer(StringBuffer sb, final Customer c) {
		if(c==null)
			return sb;
		//
		StringBuffer contacts = new StringBuffer();
		IntStream.range(0, c.contactsCount()).forEach(i ->
			contacts.append(i==0? "" : ", ").append(c.getContacts()[i])
		);
		//
		int nameStyle = 0;
		return (sb==null? new StringBuffer() : sb)
			.append(String.format("| %6d ", c.getId()))
			.append(String.format("| %-31s", fmtName(c.getFirstName(), c.getLastName(), nameStyle)))
			.append(String.format("| %-44s ", contacts))
			.append("|\n");
	}


	/**
	 * Format Customer name according to a style (0 is default):
	 * <pre>
	 * style: 0: "Meyer, Eric"  10: "MEYER, ERIC"  20: "E.M."
	 *        1: "Meyer, E."    11: "MEYER, E."    21: "E."
	 *        2: "Eric Meyer"   12: "ERIC MEYER"   22: "M."
	 *        3: "E. Meyer"     13: "E. MEYER"
	 *        4: "Eric M."      14: "ERIC M."
	 *        5: "Eric"         15: "ERIC"
	 *        6: "Meyer"        16: "MEYER"
	 * </pre>
	 * 
	 * @param firstName first name, may be null or "".
	 * @param lastName last name, may be null or "".
	 * @param style name formatting style.
	 * @return formatted name according to style.
	 */
	public String fmtName(final String firstName, final String lastName, int... style) {
		final int st = style.length > 0? style[0] : 0;	// 0 is default format
		String fn = firstName != null? firstName : "";
		String ln = lastName != null? lastName : "";
		fn = (st==1 || st==3)? fmtName(fn, "", 21) : fn;	// firstName -> "E."
		ln = (st==4)? fmtName("", ln, 22) : ln;		// lastName -> "M."
		//
		return
			(st >= 10)? (
				(st>=10 && st<20)? fmtName(firstName, lastName, st - 10).toUpperCase() :
				(st==20)? fmtName(firstName, "", 21) + fmtName("", lastName, 22) :
				(st==21)? fn.length() > 0? String.format("%s.", fn.substring(0, 1).toUpperCase()) : "" :
				(st==22)? ln.length() > 0? String.format("%s.", ln.substring(0, 1).toUpperCase()) : "" :
					""	// return as default for: st > 22
			) : (
				(fn.length() > 0 && ln.length() > 0)? (
					(st>=0 && st<=1)? String.format("%s, %s", ln, fn) :
					(st>=2 && st<=4)? String.format("%s %s", fn, ln) :
					(st==5)? fn :
					(st==6)? ln : fmtName(fn, ln)	// return default name style
			) : fn + ln);	// fn or ln (or both) are ""
	}


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
	public StringBuffer printArticles(StringBuffer sb, final Collection<Article> articles) {
		if(articles==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		return print(sb_, articles, s -> s, a -> printArticle(sb_, a));		// calling generic print method
	}


	/**
	 * Print attributes of one Article object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | SKU-458362 | Tasse                      |    299 �|  19% MwSt|
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param a Article object
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printArticle(StringBuffer sb, final Article a) {
		if(a==null)
			return sb;
		//
		return (sb==null? new StringBuffer() : sb)
			.append(String.format("| %10s ", a.getId()))
			.append(String.format("| %-27s", a.getDescription()))
			.append(String.format("| %6d ", a.getUnitPrice())).append("\u20ac")	// Unicode for Euro
			.append(String.format("| %4s MwSt", a.getTax()==TAX.GER_VAT_REDUCED? "7%" : "19%"))
			.append("|\n");
	}


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
	public StringBuffer printOrders(StringBuffer sb, final Collection<Order> orders) {
		if(orders==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		return print(sb_, orders, s -> s, a -> printOrder(sb_, a));		// calling generic print method
	}


	/**
	 * Print attributes of one Order object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | 8592356245 | Meyer, Eric                | 4 items | created: 2022-05-16 08:16:42 |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param o Order object
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printOrder(StringBuffer sb, final Order o) {
		if(o==null)
			return sb;
		//
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final String creationDate = df.format(o.getCreationDate());
		final Customer c = o.getCustomer();
		return (sb==null? new StringBuffer() : sb)
			.append(String.format("| %10s ", o.getId()))
			.append(String.format("| %-27s", fmtName(c.getFirstName(), c.getLastName(), 0)))
			.append(String.format("| %1d items ", o.itemsCount()))
			.append(String.format("| created: %s ", creationDate))
			.append("|\n");
	}
}
