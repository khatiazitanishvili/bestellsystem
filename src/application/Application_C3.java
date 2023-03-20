package application;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import datamodel.Customer;


/**
 * Runnable application class that creates and outputs {@link Customer}'s for the
 * <code>
 * <a href="{@docRoot}/index.html">{@value application.package_info#RootName}</a>.
 * </code>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Application_C3 {
	private final String appName;
	//
	private final Customer eric;
	private final Customer anne;
	private final Customer tim;
	private final Customer nadine;
	private final Customer khaled;

	/**
	 * Public main() function.
	 * 
	 * @param args arguments passed from command line.
	 */
	public static void main(String[] args) {
		var appInstance = new Application_C3();
		appInstance.run();
	}


	/**
	 * Private constructor to initialize local attributes.
	 */
	private Application_C3() {
		this.appName = "se1.Bestellsystem" + ": " + this.getClass().getSimpleName();
		//
		eric = new Customer("Eric Meyer")
			.setId(892474L)		// set id, first time
			.setId(947L)		// ignored, since id can only be set once
			.addContact("eric98@yahoo.com")
			.addContact("eric98@yahoo.com")	// ignore duplicate contact
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
	}


	/**
	 * Private method that runs with application instance.
	 */
	private void run() {
		System.out.println(this.appName);
		// 
		Set<Customer> customers = Set.of(eric, anne, tim, nadine, khaled);
		//
		StringBuffer sb = new StringBuffer("Customers:\n");
		printCustomers(sb, customers);
		//
				
		Function<Stream<Customer>, Stream<Customer>> customerStream = parameter -> 
		parameter.sorted((n1, n2) -> n1.getLastName().compareTo(n2.getLastName()));
		
		printCustomers(sb, customers,	// Aufgaben 4.), 5.)
			customerStream);
		
		Function<Stream<Customer>, Stream<Customer>> customerStream2 = parameter -> 
		parameter.filter(n -> n.getId().toString().charAt(0) == '4')
		.sorted((n1, n2) -> n1.getLastName().compareTo(n2.getLastName()));
		
		
		printCustomers(sb, customers,
			customerStream2);
		//
		System.out.println(sb);
	}


	/**
	 * Print Customer list into StringBuffer as lines with Customer attributes.
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
		sb = sb==null? new StringBuffer() : sb;
		java.util.Iterator<Customer> cit = customers.iterator();
		while(cit.hasNext()) {		// iterate through Customer collection
			Customer c = cit.next();
			printCustomer(sb, c);		// format each Customer object as line into StringBuffer
		}
		return sb;
	}


	/**
	 * Print Customer list into StringBuffer as lines with Customer attributes.
	 * Method provides control over the Stream of customer objects before formatting.
	 * <pre>
	 * Example:
	 * | 643270 | Bayer, Anne              | anne24@yahoo.de, (030) 3481-23352   |
	 * | 412396 | Blumenfeld, Nadine-Ulla  | +49 152-92454                       |
	 * | 892474 | Meyer, Eric              | eric98@yahoo.com, (030) 3945-642298 |
	 * </pre>
	 * 
	 * @param sb StringBuffer that will contains the formatted result. A new StringBuffer is created when sb is null.
	 * @param customers collection of Customers (null argument is ignored).
	 * @param customerStream functional interface to control (e.g. filter, sort) {@code Stream<Customer>} before formatting.   
	 * @return StringBuffer that contains formatted result (same sb).
	 */
	public StringBuffer printCustomers(StringBuffer sb, final Collection<Customer> customers,
			final Function<Stream<Customer>, Stream<Customer>> customerStream
	) {
		if(customers==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		//
		(customerStream != null? customerStream.apply(customers.stream()) : customers.stream())
			.forEach(c -> printCustomer(sb_, c));
		//
		return sb_;
	}


	/**
	 * Print Customer attributes into StringBuffer as column-separated line.
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
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		final int[] fw = {4, 32, 46};	// field widths
		final int nameStyle = 0;
		final String name = fmtCustomerName(c, nameStyle);
		final int len = Math.max(fw[1] - name.length() - 1, 1);
		sb_.append( "| ")
			.append(String.valueOf(c.getId())).append(" ".repeat(fw[0]-3))
			.append("| ").append(name).append(" ".repeat(len)).append("| ")
		;
		int clen = sb_.length();
		IntStream.range(0, c.contactsCount())
			.forEach(i ->
				sb_.append(i==0? "" : ", ").append(c.getContacts()[i])
			);
		String fill = " ".repeat(Math.max(fw[2] - sb_.length() + clen - 1, 1));
		return sb_.append(fill).append("|\n");
	}


	/**
	 * Format Customer name according to a style (0 is default):
	 * <pre>
	 * style: 0: "Meyer, Eric"  10: "MEYER, ERIC"
	 *        1: "Eric Meyer"   11: "ERIC MEYER"
	 *        2: "Meyer, E."    12: "MEYER, E."
	 *        3: "E. Meyer"     13: "E. MEYER"
	 *        4: "Meyer"        14: "MEYER"
	 *        5: "Eric"         15: "ERIC"
	 *        6: "E.M."
	 *        7: "EM"
	 *        8: "E"
	 *        9: "M"
	 * </pre>
	 * 
	 * @param c Customer object.
	 * @param style specify name formatting style as variable argument.
	 * @return formatted Customer name.
	 */
	public String fmtCustomerName(final Customer c, int... style) {
		if(c==null)
			throw new IllegalArgumentException("Customer null.");
		//
		String ln = c.getLastName();
		String fn = c.getFirstName();
		String fn1 = fn.substring(0, 1).toUpperCase();
		//
		switch(style.length > 0? style[0] : 0) {	// 0 is default format
		case 0: return String.format("%s, %s", ln, fn);
		case 1: return String.format("%s %s", fn, ln);
		case 2: return String.format("%s, %s.", ln, fn1);
		case 3: return String.format("%s. %s", fn1, ln);
		case 4:	return ln;
		case 5: return fn;
		case 6: return String.format("%s.%s.", fn1, fmtCustomerName(c, 9));
		case 7: return String.format("%s%s", fn1, fmtCustomerName(c, 9));
		case 8: return fn1;
		case 9: return ln.substring(0, 1).toUpperCase();
		case 10: case 11: case 12: case 13: case 14: case 15:
			return fmtCustomerName(c, style[0] - 10).toUpperCase();
		default: return fmtCustomerName(c);
		}
	}
}
