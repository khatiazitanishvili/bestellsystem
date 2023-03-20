package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for Customer class: [500..599] setName(name) extended
 * test cases:
 *  - Test cases 500: setName(name) with '-' connected multi-part last names.
 *  - Test cases 510: setName(name) with double first names.
 *  - Test cases 520: setName(name) with multiple first names.
 *  - Test cases 530: setName(name) with multiple first and last names.
 *  - Test cases 550: setName(name) with multiple, multi-dash first and last names.
 *  - Test cases 550: setName(name) with extreme long names.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Customer_500_SetNameExtendedTest {
	//
	private final Customer c1 = new Customer();


	/*
	 * Test cases 500: setName(name) with '-' connected multi-part last names.
	 */
	@Test @Order(500)
	void test500_setNameMultipartLastName() {
		c1.setName("Tim Schulz-Mueller-Meyer");		// name style 1 with multi-part last name
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller-Meyer");
	}

	@Test @Order(501)
	void test501_setNameMultipartLastName() {
		c1.setName("Schulz-Mueller-Meyer, Tim");	// name style 2
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller-Meyer");
	}

	@Test @Order(502)
	void test502_setNameMultipartLastName() {
		c1.setName("Schulz-Mueller-Meyer; Tim");	// name style 3
		assertEquals(c1.getFirstName(), "Tim");
		assertEquals(c1.getLastName(), "Schulz-Mueller-Meyer");
	}


	/*
	 * Test cases 510: setName(name) with double first names.
	 */
	@Test @Order(510)
	void test510_setNameDoubleFirstName() {
		c1.setName("Nadine Ulla Blumenfeld");	// name style 1
		assertEquals(c1.getFirstName(), "Nadine Ulla");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(511)
	void test511_setNameDoubleFirstName() {
		c1.setName("Blumenfeld, Nadine Ulla");	// name style 2
		assertEquals(c1.getFirstName(), "Nadine Ulla");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(512)
	void test512_setNameDoubleFirstName() {
		c1.setName("Blumenfeld; Nadine Ulla");	// name style 3
		assertEquals(c1.getFirstName(), "Nadine Ulla");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}


	/*
	 * Test cases 520: setName(name) with multiple first names.
	 */
	@Test @Order(520)
	void test520_setNameMultipartFirstNames() {
		c1.setName("Nadine Ulla Maxine Blumenfeld");	// name style 1
		assertEquals(c1.getFirstName(), "Nadine Ulla Maxine");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(521)
	void test521_setNameMultipartFirstNames() {
		c1.setName("Blumenfeld, Nadine Ulla Maxine");	// name style 2
		assertEquals(c1.getFirstName(), "Nadine Ulla Maxine");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}

	@Test @Order(522)
	void test522_setNameMultipartFirstNames() {
		c1.setName("Blumenfeld; Nadine Ulla Maxine");	// name style 3
		assertEquals(c1.getFirstName(), "Nadine Ulla Maxine");
		assertEquals(c1.getLastName(), "Blumenfeld");
	}


	/*
	 * Test cases 530: setName(name) with multiple first and last names.
	 */
	@Test @Order(530)
	void test530_setNameMultipartFirstNames() {
		c1.setName("Khaled Mohamed Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled Mohamed Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(531)
	void test531_setNameMultipartNames() {
		c1.setName("Saad-Abdelalim, Khaled Mohamed-Arif");	// name style 2
		assertEquals(c1.getFirstName(), "Khaled Mohamed-Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}


	/*
	 * Test cases 550: setName(name) with multiple, multi-dash first and last names.
	 */
	@Test @Order(550)
	void test550_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled-Mohamed Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled-Mohamed Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(551)
	void test551_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled-Mohamed-Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled-Mohamed-Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(552)
	void test552_setNameMultipartNames() {
		c1.setName("Khaled Mohamed-Arif Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled Mohamed-Arif");
		assertEquals(c1.getLastName(), "Saad-Abdelalim");
	}

	@Test @Order(553)
	void test553_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled-Mohamed-Arif-Saad-Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "");
		assertEquals(c1.getLastName(), "Khaled-Mohamed-Arif-Saad-Abdelalim");
	}

	@Test @Order(544)
	void test544_setNameMultiDashMultipartFirstNames() {
		c1.setName("Khaled Mohamed Arif Saad Abdelalim");	// name style 1
		assertEquals(c1.getFirstName(), "Khaled Mohamed Arif Saad");
		assertEquals(c1.getLastName(), "Abdelalim");
	}


	/*
	 * Test cases 550: setName(name) with extreme long names.
	 */
	@Test @Order(550)
	void test550_setNameExtremeLongNames() {
		c1.setName("Auguste Viktoria Friederike Luise Feodora Jenny "
				+ "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		assertEquals(c1.getFirstName(), "Auguste Viktoria Friederike Luise Feodora Jenny");
		assertEquals(c1.getLastName(), "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		//
		c1.setName("Auguste Viktoria Friederike Luise Feodora Jenny "
				+ "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		assertEquals(c1.getFirstName(), "Auguste Viktoria Friederike Luise Feodora Jenny");
		assertEquals(c1.getLastName(), "von-Schleswig-Holstein-Sonderburg-Augustenburg");
		//
		c1.setName("Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph Sylvester",
				"Buhl-Freiherr von und zu Guttenberg");
		assertEquals(c1.getFirstName(), "Karl-Theodor Maria Nikolaus Johann Jacob Philipp Franz Joseph Sylvester");
		assertEquals(c1.getLastName(), "Buhl-Freiherr von und zu Guttenberg");
	}
}
