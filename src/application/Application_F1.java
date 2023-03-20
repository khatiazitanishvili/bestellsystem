package application;

import system.IoC;
import system.Printer;
import system.TablePrinter;



/**
 * Runnable application class that creates and outputs simple orders using the
 * {@link datamodel} and {@link system} packages after refactoring.
 * <code>
 * <a href="{@docRoot}/index.html">{@value application.package_info#RootName}</a>.
 * </code>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Application_F1 {

	/**
	 * Reference to "Inversion-of-Control" container that manages system component objects.
	 */
	private final IoC ioc;

	/**
	 * Private constructor to initialize local attributes.
	 */
	private Application_F1() {
		System.out.println("se1.Bestellsystem" + ": " + this.getClass().getSimpleName());
		//
		this.ioc = IoC.getInstance();	// obtain ioc-reference from IoC interface
	}

	/**
	 * Public main() function.
	 * 
	 * @param args arguments passed from command line.
	 */
	public static void main(String[] args) {
		var appInstance = new Application_F1();
		appInstance.run();
	}


	/**
	 * Private method that runs with application instance.
	 */
	private void run() {
		//
		DatamodelFactory factory = new DatamodelFactory();
		OrderBuilder orderBuilder = new OrderBuilder(factory);
		orderBuilder
			.buildOrders()
			.buildMoreOrders()
		;
		//
		System.out.println("(" + factory.customersCount() + ") Customer objects built.");
		System.out.println("(" + factory.ordersCount() + ") Article objects built.");
		System.out.println("(" + factory.articlesCount() + ") Order objects built.");

		StringBuffer sb = new StringBuffer();
		//
		Printer printer = ioc.getPrinter();
		//
		TablePrinter orderTable =
			printer.createTablePrinter(sb, builder -> builder
				// build table columns with width and alignment (R: right aligned)
				.column("|",  11)	// "Bestell-ID"
				.column("|",  28)	// "Bestellungen", descriptions
				.column("R",   7)	// "MwSt", VAT tax for each item
				.column(" ",   1)	// " ", marker (*) for reduced VAT tax rate
				.column("R",  10)	// "Preis", price for each item
				.column("|R", 10)	// "MwSt", VAT tax for whole order
				.column(" |R",12)	// "Gesamt", price for whole order
		);
		//
		if(orderTable==null) {	// orderTable is null without component implementations
			System.out.println("==> implement components in system package");
		} else {
			orderTable
				.line()	// insert table header
				.row("Bestell-ID", "Bestellungen", "MwSt", "", "Preis", "MwSt", "Gesamt")
				.line();
			//
			printer.printOrders(orderTable, factory.getOrders());
			//
			System.out.println(sb);
		}
	}
}
