package datamodel;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Tests for Customer class: [400..499] contacts-related tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Customer_400_ContactsTest {
	//
	private final Customer c1 = new Customer();


	/*
	 * Test cases 400: add contacts.
	 */
	@Test @Order(400)
	void test400_addContactsRegularCases() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("eric@gmail.com");
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("(0152) 38230529");
		assertEquals(c1.contactsCount(), 2);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"	// must maintain order
		});
		c1.addContact("(030) 3534346-6336");
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
	}

	@Test @Order(401)
	void test401_addContactsCornerCases() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact(" eric@gmail.com  ");	// trim leading and trailing spaces
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("\t(0152) 38230529\t \n\t");	// trim more leading and
		assertEquals(c1.contactsCount(), 2);		// trailing white spaces
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"
		});
	}

	@Test @Order(402)
	void test402_addContactsCornerCases() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("\"eric@gmail.com\"");	// trim leading and trailing quotes
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("\" \"'\"(0152) 38230529';'\" ,\t\n\"");	// trim leading and trailing
		assertEquals(c1.contactsCount(), 2);	// special chars: quotes["'] and [;,.]
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"
		});
	}

	@Test @Order(403)
	void test403_addContactsMinimumLength() {
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("e@gm.c");	// minimum length for meaningful contact is 6 characters
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"e@gm.c"
		});
		//
		final String contact = "e@g.c";	// < 6 characters as contact is illegal
		IllegalArgumentException thrown =
			assertThrows(
				IllegalArgumentException.class, () -> {
					c1.addContact(contact);
		});
		assertEquals("contact less than 6 characters: \"" + contact +
				"\".", thrown.getMessage());
		//
		final String contact2 = "\"  e@g.c \t\"";	// input > 6, but not after trimming
		thrown = assertThrows(
			IllegalArgumentException.class, () -> {
				c1.addContact(contact2);
		});
		assertEquals("contact less than 6 characters: \"" + contact2 +
				"\".", thrown.getMessage());
	}

	@Test @Order(404)
	void test404_addContactsIgnoreDuplicates() {
		// duplicate entries for contacts are ignored
		assertEquals(c1.contactsCount(), 0);
		c1.addContact("eric@gmail.com");
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"
		});
		c1.addContact("eric@gmail.com");	// duplicate contact
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"				// ignored
		});
		c1.addContact("eric@gmail.com");	// duplicate contact, 2nd added
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com"				// still ignored
		});
	}


	/*
	 * Test cases 410: delete contacts.
	 */
	@Test @Order(410)
	void test410_deleteContactRegularCases() {
		c1.addContact("eric@gmail.com")
			.addContact("(0152) 38230529")
			.addContact("(030) 3534346-6336");
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
		c1.deleteContact(2);	// delete from upper bound
		assertEquals(c1.contactsCount(), 2);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529"
		});
		c1.deleteContact(0);	// delete from lower bound
		assertEquals(c1.contactsCount(), 1);
		assertArrayEquals(c1.getContacts(), new String[] { "(0152) 38230529" });
	}

	@Test @Order(411)
	void test411_deleteContactOutOfBoundsCases() {
		c1.addContact("eric@gmail.com")
			.addContact("(0152) 38230529")
			.addContact("(030) 3534346-6336");
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
		c1.deleteContact(300);	// delete > upper bound, ignore
		c1.deleteContact(3);
		assertEquals(c1.contactsCount(), 3);
		c1.deleteContact(-1);	// delete < upper bound, ignore
		c1.deleteContact(-100);
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
	}

	@Test @Order(412)
	void test412_deleteAllContacts() {
		c1.deleteAllContacts();	// nothing to delete
		assertEquals(c1.contactsCount(), 0);
		assertArrayEquals(c1.getContacts(), new String[] { });
		//
		c1.addContact("eric@gmail.com")
			.addContact("(0152) 38230529")
			.addContact("(030) 3534346-6336");
		//
		assertEquals(c1.contactsCount(), 3);
		assertArrayEquals(c1.getContacts(), new String[] {
			"eric@gmail.com", "(0152) 38230529", "(030) 3534346-6336"
		});
		//
		c1.deleteAllContacts();	// all contacts deleted
		assertEquals(c1.contactsCount(), 0);
		assertArrayEquals(c1.getContacts(), new String[] { });
		//
		c1.deleteAllContacts();	// repeat
		assertEquals(c1.contactsCount(), 0);
		assertArrayEquals(c1.getContacts(), new String[] { });
	}
}
