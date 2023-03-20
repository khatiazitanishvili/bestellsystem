package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import datamodel.Article;
import datamodel.Currency;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;
import system.TablePrinter;


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

public class Application_E1 {
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
	private Application_E1() {
		System.out.println(package_info.RootName + ": " + this.getClass().getSimpleName());
		//
		this.customers = buildCustomers();
		this.articles = buildArticles();
		this.orders = buildOrders();
		//
		moreOrders();
	}


	/**
	 * Public main() function.
	 * 
	 * @param args arguments passed from command line.
	 */
	public static void main(String[] args) {
		var appInstance = new Application_E1();
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
		process(sb.append("\nCustomers_sorted by name:\n"), customers,
			stream -> stream.sorted((c1, c2) -> c1.getLastName().compareTo(c2.getLastName())),
			c -> printCustomer(sb, c));
		//
		printArticles(sb.append("\nArticles:\n"), articles);
		//
		process(sb.append("\nArticles_sorted by price:\n"), articles,
			stream -> stream.sorted((a1, a2) -> Long.compare(a1.getUnitPrice(), a2.getUnitPrice())),
			a -> printArticle(sb, a));
		//
		printOrders(sb.append("\nOrders:\n"), orders);

		TablePrinter orderTable = createTablePrinter(sb, builder -> builder
			// builder defining table columns with width and alignment (R: right aligned)
			.column("|",  11)	// "Bestell-ID"
			.column("|",  28)	// "Bestellungen", descriptions
			.column("R",   7)	// "MwSt", VAT tax for each item
			.column(" ",   1)	// " ", marker (*) for reduced VAT tax rate
			.column("R",  10)	// "Preis", price for each item
			.column("|R", 10)	// "MwSt", VAT tax for whole order
			.column(" |R",12)	// "Gesamt", price for whole order
		);

		printOrder(orderTable
			.line()	// table header
			.row("Bestell-ID", "Bestellungen", "MwSt", "", "Preis", "MwSt", "Gesamt")
			.line(), o8592)
		.line();
		//
		printOrder(orderTable
			.line()	// table header
			.row("Bestell-ID", "Bestellungen", "MwSt", "", "Preis", "MwSt", "Gesamt")
			.line(), o3563)
		.line();

		printOrders(orderTable
			.line()	// table header
			.row("Bestell-ID", "Bestellungen", "MwSt", "", "Preis", "MwSt", "Gesamt")
			.line(), orders);

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
		tasse  = new Article("Tasse",  299).setId("SKU-458362");
		becher = new Article("Becher", 149).setId("SKU-693856");
		kanne  = new Article("Kanne", 1999).setId("SKU-518957");
		teller = new Article("Teller", 649).setId("SKU-638035");
		//
		buch_Java = new Article("Buch \"Java\"", 4990).setId("SKU-278530")
				.setTax(TAX.GER_VAT_REDUCED);	// reduced tax rate for books
		//
		buch_OOP = new Article("Buch \"OOP\"", 7995).setId("SKU-425378")
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
		//
		// Eric's 1st order
		o8592 = new Order(eric)	// new order for Eric
				.setId("8592356245")	// assign order-id: 8592356245
				// add items to order
				.addItem(teller, 4)		// 4 Teller, 4x 6.49 €
				.addItem(becher, 8)		// 8 Becher, 8x 1.49 €
				.addItem(buch_OOP, 1)	// 1 Buch "OOP", 1x 79.95 €, 7% MwSt (5.23€)
				.addItem(tasse, 4);		// 4 Tassen, 4x 2.99 €
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
	 * Private builder method for adding more Customer, Article and Order objects.
	 * 
	 */
	private void moreOrders() {
		int csz = customers.size();
		int asz = articles.size();
		int osz = orders.size();
		
		/*
		 * TODO: E1(4) add new articles, orders, customers accroding to assignment.
		 */
		Article pfanne = new Article("Pfanne", 4999).setId("SKU-300926");
		Article fahrradhelm = new Article("Fahrradhelm", 16900).setId("SKU‐663942");
		Article fahrradkarte = new Article("Fahrradkarte", 695).setId("SKU‐583978").setTax(TAX.GER_VAT_REDUCED);
		//
		this.articles.addAll(Arrays.asList(pfanne, fahrradhelm, fahrradkarte));

		// Eric's 3rd order
		Order o7356 = new Order(eric)
				.setId("7372561535")
				.addItem(fahrradhelm, 1)
				.addItem(fahrradkarte, 1);
		//
		// Eric's 4th order
		Order o4450 = new Order(eric)
				.setId("4450305661")
				.addItem(tasse, 3)
				.addItem(becher, 3)
				.addItem(kanne, 1);
		//
		// Neukundin Lena Neumann
		Customer lena = new Customer("Lena Neumann");
		lena.addContact("lena228@gmail.com");
		customers.add(lena);
		Order o111 = new Order(lena)
				.setId("651286")
				.addItem(buch_Java, 1)
				.addItem(fahrradkarte, 1);
		
		//
		orders.addAll(Arrays.asList(o7356, o4450, o111));
		//
		System.out.println("  ...adding more Customer, Article and Order objects:");
		System.out.println("(+" + (customers.size() - csz) + ") Customer objects added.");
		System.out.println("(+" + (articles.size() - asz) + ") Article objects added.");
		System.out.println("(+" + (orders.size() - osz) + ") Order objects added.");
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
	 * Generic method that converts a {@code Collection<T>} to {@code Stream<T>}
	 * and applies a function @{code applyEach} to each element.
	 * 
	 * @param <T> generic {@code Collection} and {@code Stream} element type.
	 * @param <R> generic return type of a collector.
	 * @param collector collector with result. 
	 * @param collection elements to be processed.
	 * @param applyEach functional interface to process each element {@code t}.
	 * @return collector with result.
	 */
	public <T,R> R process(final R collector, final Collection<T> collection,
			final Consumer<T> applyEach
	) {
		return process(collector, collection, null, applyEach);
	}


	/**
	 * Generic method that converts a {@code Collection<T>} to {@code Stream<T>},
	 * allows a callout to be applied to the stream before a function @{code applyEach}
	 * is applied to each remaining element.
	 * 
	 * @param <T> generic {@code Collection} and {@code Stream} element type.
	 * @param <R> generic return type of a collector.
	 * @param collector collector with result. 
	 * @param collection elements to be processed.
	 * @param callout functional interface to process stream of elements before processing function is applied.
	 * @param applyEach functional interface to process each element {@code t}.
	 * @return collector with result.
	 */
	public <T,R> R process(final R collector, final Collection<T> collection,
			final Function<Stream<T>, Stream<T>> callout,
			final Consumer<T> applyEach
	) {
		if(collection != null) {
			(callout != null? callout.apply(collection.stream()) : collection.stream())
				.forEach(t -> applyEach.accept(t));
		}
		return collector;
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
	public StringBuffer printCustomer(final StringBuffer sb, final Customer c) {
		if(c==null)
			return sb;
		//
		final StringBuffer contacts = new StringBuffer();
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
	public StringBuffer printCustomers(final StringBuffer sb, final Collection<Customer> customers) {
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
		return process(sb_, customers, s -> s, c -> printCustomer(sb_, c));	// calling generic print method
	}


	/**
	 * Print attributes of one Article object into StringBuffer as column-separated line.
	 * <pre>
	 * Example:
	 * | SKU-458362 | Tasse                      |    299 €|  19% MwSt|
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param a Article object
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printArticle(final StringBuffer sb, final Article a) {
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
	 * Print collection of Article objects into StringBuffer as lines with Article attributes.
	 * <pre>
	 * Example:
	 * | SKU-458362 | Tasse                      |    299 €|  19% MwSt|
	 * | SKU-693856 | Becher                     |    149 €|  19% MwSt|
	 * | SKU-278530 | Buch "Java"                |   4990 €|   7% MwSt|
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param articles collection of Articles (null argument is ignored).
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printArticles(final StringBuffer sb, final Collection<Article> articles) {
		if(articles==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		return process(sb_, articles, a -> printArticle(sb_, a));
	}


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
	public StringBuffer printOrder(final StringBuffer sb, final Order order) {
		if(order==null)
			return sb;
		//
		final String creationDate = fmtDate(order.getCreationDate(), 0, "");
		final Customer c = order.getCustomer();
		return (sb==null? new StringBuffer() : sb)
			.append(String.format("| %10s ", order.getId()))
			.append(String.format("| %-27s", fmtName(c.getFirstName(), c.getLastName(), 0)))
			.append(String.format("| %1d items ", order.itemsCount()))
			.append(String.format("| created: %s ", creationDate))
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
	public StringBuffer printOrders(final StringBuffer sb, final Collection<Order> orders) {
		if(orders==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		return process(sb_, orders, a -> printOrder(sb_, a));
	}


	/**
	 * Print order into TablePrinter with order item separated lines.
	 * <pre>
	 * Example:
	 * +----------+---------------------------------------------+--------------------+
	 * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
	 * +----------+---------------------------------------------+--------------------+
	 * |8592036245|Eric's Bestellung                            |                    |
	 * |          | - 4 Teller, 4x 6.49          4.14     25.96€|                    |
	 * |          | - 8 Becher, 8x 1.49          1.90     11.92€|                    |
	 * |          | - 1 Buch "OOP"               5.23*    79.95€|                    |
	 * |          | - 4 Tasse, 4x 2.99           1.91     11.96€|   13.18€    129.79€|
	 * +----------+---------------------------------------------+--------------------+
	 * </pre>
	 * 
	 * @param orderTable to print order into.
	 * @param order order printed into {@code orderTable}, (null argument is ignored).
	 * @return {@link TablePrinter} that contains formatted result.
	 */
	public TablePrinter printOrder(final TablePrinter orderTable, final Order order) {
		//
		if(orderTable != null && order != null) {
			/*
			 * TODO: E1(3) implement/change logic to extract items from order, calculate values
			 * and fill them into table rows. Code should produce correct table for any order.
			 * 
			 * Current code produces (should produce what is shown in javadoc):
			 * +----------+---------------------------------------------+--------------------+
			 * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
			 * +----------+---------------------------------------------+--------------------+
			 * |8592356245|Eric's Bestellung:                           |                    |
			 * |          | - 4 Teller, 4x 6.49          4.14*    25.96€|                    |
			 * |          | - 4 Tasse, 4x 2.99           1.91*    11.96€|   13.18€    129.79€|
			 * +----------+---------------------------------------------+--------------------+
			 */
			String id = order.getId();	// retrieve order attributes
			String name = order.getCustomer().getFirstName();
			orderTable.row(id, name + "'s Bestellung: ");
			//
			int length = order.itemsCount();
			int count = 0;
			long gesamtMwSt = 0;
			long gesamt = 0;
			
			for(OrderItem oi : order.getItems()) {
				count++;
				Article article = oi.getArticle();
				int units = oi.getUnitsOrdered();
				long price = article.getUnitPrice();
				String des = article.getDescription();
				
				long mwst = calculateIncludedVAT(price*units, article.getTax());
				String itemVATStr = fmtPrice(mwst, 1);			// 414 => "4.14"
				gesamtMwSt += mwst;
				
				String priceString = fmtPrice(price,1);
				String itemPriceStr = fmtPrice(units*price, 1);	// "25.96€" with Euro symbol
				gesamt += units*price;
				
				String reducedTaxMarker = "";
				if(article.getTax() == TAX.GER_VAT_REDUCED) {
					reducedTaxMarker = "*";
				}
				
				//orderTable.row("", " - 4 Teller, 4x 6.49", itemVATStr, reducedTaxMarker, itemPriceStr, "0.00€", "0.00€");
				if(units == 1) {
					if(count == length) {
						orderTable.row("", " - "+ units + " " + des + "", itemVATStr, reducedTaxMarker, itemPriceStr, fmtPrice(gesamtMwSt, 1), fmtPrice(gesamt, 1));
						break;
					}
					orderTable.row("", " - "+ units + " " + des + "", itemVATStr, reducedTaxMarker, itemPriceStr, "", "");
					continue;
				}
				if(count == length) {
					orderTable.row("", " - "+ units + " " + des + ", " + units + "x "+ priceString + "", itemVATStr, reducedTaxMarker, itemPriceStr, fmtPrice(gesamtMwSt, 1), fmtPrice(gesamt, 1));
				} else {
					orderTable.row("", " - "+ units + " " + des + ", " + units + "x "+ priceString + "", itemVATStr, reducedTaxMarker, itemPriceStr, "", "");
				}
			}
		}
		return orderTable;
	}


	/**
	 * Print collection of order objects into TablePrinter.
	 * <pre>
	 * Example:
	 * +----------+---------------------------------------------+--------------------+
	 * |Bestell-ID|Bestellungen                  MwSt      Preis|     MwSt     Gesamt|
	 * +----------+---------------------------------------------+--------------------+
	 * |8592356245|Eric's Bestellung:                           |                    |
	 * |          | - 4 Teller, 4x 6.49          4.14     25.96€|                    |
	 * |          | - 8 Becher, 8x 1.49          1.90     11.92€|                    |
	 * |          | - 1 Buch "OOP"               5.23*    79.95€|                    |
	 * |          | - 4 Tasse, 4x 2.99           1.91     11.96€|   13.18€    129.79€|
	 * +----------+---------------------------------------------+--------------------+
	 * |3563561357|Anne's Bestellung:                           |                    |
	 * |          | - 2 Teller, 2x 6.49          2.07     12.98€|                    |
	 * |          | - 2 Tasse, 2x 2.99           0.95      5.98€|    3.02€     18.96€|
	 * +----------+---------------------------------------------+--------------------+
	 *  {@code >>>>>>>>>>}                                       Gesamt:|   16.20€    148.75€|
	 *                                                          +====================+
	 * </pre>
	 * 
	 * @param orderTable to print orders into.
	 * @param orders collection of orders printed into {@code orderTable}, (null argument is ignored).
	 * @return {@link TablePrinter} that contains formatted result.
	 */
	public TablePrinter printOrders(final TablePrinter orderTable, final Collection<Order> orders) {
		long[] totals = {	// tuple with compounded price and VAT tax values over all orders.
				0L,		// compounded order value stored in first element
				0L		// compounded order VAT tax stored in second element
		};
		
		long[] totals2;
		for(Order order : orders) {
			totals2 =calculateValueAndTax(order);
			totals[0] += totals2[0];
			totals[1] += totals2[1];
			printOrder(orderTable, order).line();
		}
		
		//
		/*
		 * TODO: E1(5) implement/change logic.
		 */
		
//		printOrder(orderTable, o8592).line();
//		printOrder(orderTable, o8592).line();
//		printOrder(orderTable, o8592).line();
		//
		String totalPrice = fmtPrice(totals[0], 1);
		String totalVAT = fmtPrice(totals[1], 1);
		//
		process(null, null, null, null);
		
		return orderTable
			.row( "@ >        |   |", "", "", "", "", "Gesamt:", totalVAT, totalPrice)
			.line("@          +=+=+");
	}


	/**
	 * Applicable tax rate mapped from TAX enum.
	 */
	private final Map<TAX, Double> taxRateMapper = Map.of(
		TAX.TAXFREE,			 0.0,	// tax free rate
		TAX.GER_VAT,			19.0,	// German VAT tax (MwSt) 19.0%
		TAX.GER_VAT_REDUCED,	 7.0	// German reduced VAT tax (MwSt) 7.0%
	);


	/**
	 * Get percent tax rate from enum value.
	 * 
	 * @param taxRate enum value of applicable tax rate.
	 * @return tax rate in percent.
	 */
	public double getTaxRate(final TAX taxRate) {
		return taxRate != null? taxRateMapper.get(taxRate) : 0.0;
	}


	/**
	 * Calculate included VAT tax from gross price/value based on specific
	 * tax rate (VAT is value-added tax, in Germany it is called
	 * <i>"Mehrwertsteuer" (MwSt.)</i>).
	 * 
	 * @param grossValue value that included tax.
	 * @param tax applicable tax rate.
	 * @return tax included in gross value based on tax rate.
	 */
	public long calculateIncludedVAT(final long grossValue, final TAX tax) {
		/*
		 * TODO: E1(2) implement formula to calculate included VAT tax.
		 */
		
		double taxrate = getTaxRate(tax);
		double calc = grossValue / (100+taxrate)*taxrate;
		long vat = Math.round(calc);
		return vat;
	}


	/**
	 * Calculate compounded value and VAT tax over all order items.
	 * 
	 * @param order order to calculate compounded value and VAT tax.
	 * @return tuple with compounded value and VAT tax of order items.
	 */
	public long[] calculateValueAndTax(final Order order) {
		long[] totals = {0L, 0L};
		Optional.ofNullable(order).ifPresent(o -> o.getItems().forEach(item -> {
			int units = item.getUnitsOrdered();
			long unitPrice = item.getArticle().getUnitPrice();
			long itemPrice = unitPrice * units;
			long vat = calculateIncludedVAT(itemPrice, item.getArticle().getTax());
			totals[0] += itemPrice;	// compound item price
			totals[1] += vat;		// compound item tax	
		}));
		return totals;	// return tuple with compounded {value, vat}
	}


	/**
	 * Currency symbols defined by ASCII/Unicode-Strings mapped from Currency enum.
	 */
	final Map<Currency, String> CurrencySymbol = Map.of(
		Currency.EUR, "\u20ac",		// Unicode: EURO
		Currency.USD, "$",			// ASCII: US Dollar
		Currency.GBP, "\u00A3",		// Unicode: UK Pound Sterling
		Currency.YEN, "\u00A5",		// Unicode: Japanese Yen
		Currency.BTC, "BTC"			// no Unicode for Bitcoin
	);

	/**
	 * EUR currency symbol.
	 */
	final String EUR = CurrencySymbol.get(Currency.EUR);


	/**
	 * Format Customer name according to a style (0 is default):
	 * <pre>
	 * style: 0: "Meyer, Eric"    10: "MEYER, ERIC"    20: "E.M."
	 *        1: "Meyer, E."      11: "MEYER, E."      21: "E."
	 *        2: "Eric Meyer"     12: "ERIC MEYER"     22: "M."
	 *        3: "E. Meyer"       13: "E. MEYER"
	 *        4: "Eric M."        14: "ERIC M."
	 *        5: "Eric"           15: "ERIC"
	 *        6: "Meyer"          16: "MEYER"
	 * </pre>
	 * 
	 * @param firstName first name, may be null or "".
	 * @param lastName last name, may be null or "".
	 * @param style name formatting style.
	 * @return formatted name according to style.
	 */
	public String fmtName(final String firstName, final String lastName, final int... style) {
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
	 * DateFormatter for date formats.
	 */
	private static final DateFormat[] dateFmts = {
		new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),				// 0: 2022-05-17 18:55:43
		new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"),				// 1: 17-05-2022 18:55:43
		null,														// 2: 1652823817740, as long-String
		//
		new SimpleDateFormat("yyyy-MM-dd"),							// 3: 2022-05-17
		new SimpleDateFormat("dd-MM-yyyy"),							// 4: 17-05-2022
		new SimpleDateFormat("MM/dd/yy", Locale.US),				// 5: 05/17/22
		new SimpleDateFormat("MM/dd/yyyy", Locale.US),				// 6: 05/17/2022
		DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US),	// 7: May 17, 2022
		//
		DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN),	// 8: 17.05.22
		DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN),	// 9: 17.05.2022
		DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMAN),		//10: 17. Mai 2022
		new SimpleDateFormat("EEE", Locale.GERMAN),						//11: Di
		new SimpleDateFormat("EEEE", Locale.GERMAN),					//12: Dienstag
		new SimpleDateFormat("EEEE, 'der' dd. MMM yyyy",Locale.GERMAN),	//13: Dienstag, der 17. Mai 2022
		new SimpleDateFormat("EEE", Locale.US),							//14: Tue
		new SimpleDateFormat("EEEE", Locale.US),						//15: Tuesday
		new SimpleDateFormat("EEE, MMM dd, yyyy",Locale.US),			//16: Tue, May 05, 2022
	};

	/**
	 * DateFormatter for time formats.
	 */
	private static final DateFormat[] timeFmts = {
		new SimpleDateFormat("HH:mm:ss"),				// 0: 18:55:43
		new SimpleDateFormat("HH:mm"),					// 1: 18:55
		new SimpleDateFormat("HH:mm:ss.SSS"),			// 2: 18:55:43.348
		new SimpleDateFormat("HH:mm:ss, z"),			// 3: 18:55:43, CEST
		new SimpleDateFormat("HH:mm:ss, Z"),			// 4: 18:55:43, +0200
		new SimpleDateFormat("HH:mm:ss, aa")			// 5: 06:55:43 PM
	};


	/**
	 * Format date/time from long value (64 bit, counted ms since 01/01/1970).
	 * Date and time styles can be combined, e.g. with dateStyle 4, fill: "at"
	 * and timeStyle 3 returning: "17-05-2022 at 18:55:43, CEST".
	 * <pre>
	 * dateStyle:                          timeStyle:
	 *     0: "2022-05-17 18:55:43" (*)        0: "18:55:43"
	 *     1: "17-05-2022 18:55:43" (*)        1: "18:55"
	 *     2: "1652823817740"       (*)        2: "18:55:43.348"
	 *     3: "2022-05-17"                     3: "18:55:43, CEST"
	 *     4: "17-05-2022"                     4: "18:55:43, +0200"
	 *     5: "05/17/22"                       5: "06:55:43 PM"
	 *     6: "05/17/2022"
	 *     7: "May 17, 2022"
	 *     8: "17.05.22"
	 *     9: "17.05.2022"
	 *    10: "17. Mai 2022"
	 *    ------------------------------------------------------------
	 *    11: "Di"
	 *    12: "Dienstag"
	 *    13: "Dienstag, der 17. Mai 2022"
	 *    14: "Tue"
	 *    15: "Tuesday"
	 *    16: "Tue, May 05, 2022"
	 *    (*) timeStyle option has no effect.
	 * </pre>
	 * 
	 * @param datetime date/time 64 bit value, counted ms since 01/01/1970.
	 * @param dateStyle date formatting style ({@code 0} is default).
	 * @param fill String filled between date and time (space is default).
	 * @param timeStyle time formatting style ({@code 0} is default).
	 * @return formatted date or time according to style.
	 */
	public String fmtDate(final long datetime, final int dateStyle, final String fill, final int... timeStyle) {
		int tst = timeStyle.length > 0? timeStyle[0] : -1;
		String datetimeStr = "";
		DateFormat df = dateStyle >= 0 && dateStyle < dateFmts.length? dateFmts[dateStyle] : null;
		datetimeStr = dateStyle==2?
			Long.toUnsignedString(datetime) :	// case 2: return long number as String
			df==null? "" : df.format(datetime);	// remaining cases
		//
		DateFormat tf = tst >= 0 && tst < timeFmts.length? timeFmts[tst] : null;
		if(tf != null && dateStyle > 2) {
			datetimeStr += fill != null? fill : " ";
			datetimeStr += tf.format(datetime);
		}
		return datetimeStr;
	}


	/**
	 * Format long value to price according to a style (0 is default):
	 * <pre>
	 * Example: long value: 499
	 * style: 0: "4.99"
	 *        1: "4.99€"
	 *        2: "4.99$"
	 *        3: "4.99£"
	 *        4:  "499¥"
	 *        5:  "499"
	 * </pre>
	 * 
	 * @param price long value as price.
	 * @param style price formatting style.
	 * @return formatted price according to style.
	 */
	public String fmtPrice(final long price, final int... style) {
		final int st = style.length > 0? style[0] : 0;	// 0 is default format
		return
			st==0? fmtDecimal(price, 2) :
			st==1? fmtDecimal(price, 2, EUR) :
			st==2? fmtDecimal(price, 2, CurrencySymbol.get(Currency.USD)) :
			st==3? fmtDecimal(price, 2, CurrencySymbol.get(Currency.GBP)) :
			st==4? fmtDecimal(price, 0, CurrencySymbol.get(Currency.YEN)) :
			st==5? fmtDecimal(price, 0) :
			"";
	}


	/**
	 * Method to format a long value to a decimal String with a specified
	 * number of digits.
	 * 
	 * @param value value to format to String in decimal format.
	 * @param decimalDigits number of digits.
	 * @param unit appended unit as String.
	 * @return formatted decimal value as String.
	 */
	public String fmtDecimal(final long value, final int decimalDigits, final String... unit) {
		final String unitStr = unit.length > 0? unit[0] : null;
		final Object[][] dec = {
			{      "%,d", 1L },		// no decimal digits:  16,000Y
			{ "%,d.%01d", 10L },
			{ "%,d.%02d", 100L },	// double-digit price: 169.99E
			{ "%,d.%03d", 1000L },	// triple-digit unit:  16.999-
		};
		String result;
		String fmt = (String)dec[decimalDigits][0];
		if(unitStr != null && unitStr.length() > 0) {
			fmt += "%s";	// add "%s" to format for unit string
		}
		int decdigs = Math.max(0, Math.min(dec.length - 1, decimalDigits));
		//
		if(decdigs==0) {
			Object[] args = {value, unitStr};
			result = String.format(fmt, args);
		} else {
			long digs = (long)dec[decdigs][1];
			long frac = Math.abs( value % digs );
			Object[] args = {value/digs, frac, unitStr};
			result = String.format(fmt, args);
		}
		return result;
	}


	/**
	 * Factory method to create TablePrinter instances.
	 * 
	 * @param sb StringBuffer to collect table content.
	 * @param builder initialize table at creation with columns, widths and alignment.
	 * @return TablePrinter instance.
	 */
	TablePrinter createTablePrinter(final StringBuffer sb, final Consumer<TablePrinter.Builder> builder) {
		return new TablePrinterImpl(sb, builder);
	}


	/**
	 * Class that implements the TablePrinter interface of a table comprised of
	 * columns defined by {@code width(number)}, alignment: {@code 'L'} for left
	 * and {@code 'R'} for right alignment, and a fill character (default is space).
	 * 
	 * Horizontal lines and rows with content can be inserted into that
	 * structure.
	 */
	private static class TablePrinterImpl implements TablePrinter {
		final List<Column> columns = new ArrayList<Column>();
		final String rowSpec;	// default row spec:  "| | | |"
		final String lineSpec;	// default line spec: "+-+-+-+"
		final StringBuffer sb;
		final static char SPACE = 0x20;
		final static char NUL = 0x00;
		final static char L = 'L';
		final static char R = 'R';
		enum ALIGN {L, R};			// left, right column alignment

		class Column {
			final boolean lb, rb;	// has left, right column border
			final char fill;		// fill character, default is SPACE
			final ALIGN align;		// column alignment
			final int width;		// column width
			//
			Column(Column prev, String spec, int width) {
				int len = spec != null? spec.length() : -1;
				boolean prevColHasRightBorder = prev != null && prev.rb;
				this.lb = ! prevColHasRightBorder && len > 0 && spec.charAt(0)=='|';
				int fi = len > 0 && spec.charAt(0)=='|'? 1 : 0;
				char cf = len > fi? spec.charAt(fi) : NUL;
				this.fill = cf < SPACE || cf==L||cf==R||cf=='|'? SPACE : cf;
				char cl = len > 0? spec.charAt(len-1) : NUL;
				this.align = cl==R? ALIGN.R : ALIGN.L;	// left-aligned column default
				this.rb = cl!=L && cl!=R? len>1 && cl=='|'
						: (len > 2? spec.charAt(len-2) : NUL)=='|';
				this.width = width > 0? width - (lb? 1 : 0) - (rb? 1 : 0) : 0;
			}
		}

		TablePrinterImpl(StringBuffer sb, Consumer<Builder> builder) {
			this.sb = sb==null? new StringBuffer() : sb;
			builder.accept(new Builder() {
				@Override
				public Builder column(String spec, int width) {
					Column prev = columns.size() > 0? columns.get(columns.size()-1) : null;
					columns.add(new Column(prev, spec, width));
					return this;
				}
			});
			this.rowSpec = "| ".repeat(columns.size()) + "|";
			this.lineSpec = "+-".repeat(columns.size()) + "+";
		}

		@Override
		public TablePrinter line() { return render(lineSpec); }

		@Override
		public TablePrinter line(String spec) { return render(spec); }

		@Override
		public TablePrinter row(String... args) {
			int lena = args != null? args.length : -1;
			String arg0 = lena > 0? args[0] : "";
			boolean hasSpec = arg0.startsWith("@");	// shift args[] << 1
			String[] args2 = hasSpec? Arrays.copyOfRange(args, 1, lena) : args;
			return render(hasSpec? arg0 : rowSpec, args2);
		}

		@Override
		public void print(PrintStream ps) { ps.print(sb); }

		private TablePrinter render(String spec, String... args) {
			int lens = spec != null? spec.length() : -1;
			int lena = args != null? args.length : -1;
			spec = lens > 0 && spec.startsWith("@")? spec.substring(1) : spec;
			// IntStream.range(0, columns.size()).forEach( i -> {
			for(int i=0; i < columns.size() && i < lens/2; i++) {
				Column col = columns.get(i);
				int j = i*2;
				if(col.lb && j < lens) {
					sb.append(String.valueOf(spec.charAt(j)));
				}
				if(++j < lens || col.fill != SPACE) {
					String text = i < lena && args[i] != null? args[i] : "";
					int d = col.width - text.length();
					if(d > 0) {	// fill to width from left or right
						char fc = spec==rowSpec && col.fill != SPACE? col.fill : NUL;
						fc = fc==NUL && i < lens? spec.charAt(j) : fc;
						String fill = String.valueOf(fc).repeat(d);
						boolean left = col.align==ALIGN.L;
						sb.append(left? text : fill).append(left? fill : text);
					}
					if(d < 0) {	// cut to width
						sb.append(col.align==ALIGN.R? text.substring(-d)// cut from left
							: text.substring(0, text.length() + d));	// cut from right
					}
					if(d==0) {
						sb.append(text);
					}
				}
				if(col.rb && ++j < lens) {
					sb.append(String.valueOf(spec.charAt(j)));
				}
			};
			sb.append("\n");
			return this;
		}
	};
}
