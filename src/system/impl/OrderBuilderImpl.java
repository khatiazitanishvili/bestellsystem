package system.impl;

//import application.package_info;
import datamodel.Article;
import datamodel.Customer;
import datamodel.TAX;
import system.DatamodelFactory;
import system.OrderBuilder;


/**
 * OrderBuilder builds sample orders using objects from the {@link datamodel} package.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class OrderBuilderImpl implements OrderBuilder {

	/**
	 * Factory from which objects are built.
	 */
	private final DatamodelFactory factory;


	/**
	 * Public constructor.
	 * 
	 * @param factory factory from which objects are built.
	 */
	public OrderBuilderImpl(DatamodelFactory factory) {
		this.factory = factory;
	}


	/**
	 * Method to build a first set of Customer, Article and Order objects.
	 * 
	 * @return chainable self-reference.
	 */
	public OrderBuilder buildOrders() {
		//
		// creating Customers:
		Customer eric = factory.createCustomer("Eric Meyer")
			.setId(892474L)
			.addContact("eric98@yahoo.com")
			.addContact("(030) 3945-642298");
		//
		Customer anne = factory.createCustomer("Bayer, Anne")
			.setId(643270L)
			.addContact("anne24@yahoo.de")
			.addContact("(030) 3481-23352")
			.addContact("fax: (030)23451356");
		//
		factory.createCustomer("Tim Schulz-Mueller")
			.setId(286516L)
			.addContact("tim2346@gmx.de");
		//
		Customer nadine = factory.createCustomer("Nadine-Ulla Blumenfeld")
			.setId(412396L)
			.addContact("+49 152-92454");
		//
		factory.createCustomer()
			.setName("Khaled Saad Mohamed Abdelalim")
			.setId(456454L)
			.addContact("+49 1524-12948210");
		//
		// creating Articles:
		Article tasse  = factory.createArticle("Tasse",  299).setId("SKU-458362");
		Article becher = factory.createArticle("Becher", 149).setId("SKU-693856");
		Article kanne  = factory.createArticle("Kanne", 1999).setId("SKU-518957");
		Article teller = factory.createArticle("Teller", 649).setId("SKU-638035");
		//
		Article buch_Java = factory.createArticle("Buch \"Java\"", 4990).setId("SKU-278530")
				.setTax(TAX.GER_VAT_REDUCED);	// reduced tax rate for books
		//
		Article buch_OOP = factory.createArticle("Buch \"OOP\"", 7995).setId("SKU-425378")
				.setTax(TAX.GER_VAT_REDUCED);	// reduced tax rate for books
		//
		// creating Orders:
		//
		// Eric's 1st order, o8592
		factory.createOrder(eric)	// new order for Eric
				.setId("8592356245")	// assign order-id: 8592356245
				// add items to order
				.addItem(teller, 4)		// 4 Teller, 4x 6.49 �
				.addItem(becher, 8)		// 8 Becher, 8x 1.49 �
				.addItem(buch_OOP, 1)	// 1 Buch "OOP", 1x 79.95 �, 7% MwSt (5.23�)
				.addItem(tasse, 4);		// 4 Tassen, 4x 2.99 �
		//
		// Anne's order, o3563
		factory.createOrder(anne)
				.setId("3563561357")
				.addItem(teller, 2)
				.addItem(tasse, 2);
		//
		// Eric's 2nd order, o5234
		factory.createOrder(eric)
				.setId("5234968294")
				.addItem(kanne, 1);
		//
		// Nadine's order, o6135
		factory.createOrder(nadine)
				.setId("6135735635")
				.addItem(teller, 12)
				.addItem(buch_Java, 1)
				.addItem(buch_OOP, 1);
		//
		return this;
	}


	/**
	 * Method to build another set of Customer, Article and Order objects.
	 * 
	 * @return chainable self-reference.
	 */
	public OrderBuilder buildMoreOrders() {
		//
		// find customers and articles needed to build more orders
		Customer eric = factory.findCustomerById(892474L).get();
		Article tasse = factory.findArticleById("SKU-458362").get();
		Article becher = factory.findArticleById("SKU-693856").get();
		Article kanne = factory.findArticleById("SKU-518957").get();
		Article buch_Java = factory.findArticleById("SKU-278530").get();
		//
		factory.createArticle("Pfanne", 4999).setId("SKU-300926");
		Article helm = factory.createArticle("Fahrradhelm", 16900).setId("SKU-663942");
		Article karte = factory.createArticle("Fahrradkarte", 695).setId("SKU-583978")
				.setTax(TAX.GER_VAT_REDUCED);	// reduced tax rate for books
		//
		// Eric's 3rd order, o7356
		factory.createOrder(eric)
				.setId("7372561535")
//					.setCreationDate(parseDateTime("12/26/2021 14:01:09"))
				.addItem(helm, 1)
				.addItem(karte, 1);
		//
		// Eric's 4th order, o4450
		factory.createOrder(eric)
				.setId("4450305661")
//					.setCreationDate(parseDateTime("04/08/2021 04:21:18"))
				.addItem(tasse, 3)
				.addItem(becher, 3)
				.addItem(kanne, 1);

		// new Customer Lena
		Customer lena = factory.createCustomer("Lena Neumann")
				.setId(651286L)
				.addContact("lena228@gmail.com");
		//
		// Lena's order, o6173
		factory.createOrder(lena)
				.setId("6173043537")
//					.setCreationDate(parseDateTime("02/12/2022 18:07:02"))
				.addItem(buch_Java, 1)
				.addItem(karte, 1);
		//
		return this;
	}
}

