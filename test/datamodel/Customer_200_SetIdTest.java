package datamodel;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


/**
 * Tests for Customer class: [200..299] setId() tests.
 * 
 * @author sgra64
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Customer_200_SetIdTest {
	//
	private final Customer c1 = new Customer();		// test-object, "unit-under-test"


	/*
	 * Test cases 200: set id with regular value.
	 */
	@Test @Order(200)
	void test200_setIdRegularValue() {
		assertEquals(c1.getId(), null);	// returns null for unassigned id
		c1.setId(3L);					// assign id with long value
		assertEquals(c1.getId(), 3L);	// return assigned id
	}


	/*
	 * Test case: set id with smallest regular value.
	 */
	@Test @Order(201)
	void test201_setIdMinValue() {
		c1.setId(0L);
		assertEquals(c1.getId(), 0L);
	}


	/*
	 * Test case: set id with largest regular value.
	 */
	@Test @Order(202)
	void test202_setIdMaxValue() {
		c1.setId(Long.MAX_VALUE);
		assertEquals(c1.getId(), Long.MAX_VALUE);
	}


	/*
	 * Test cases 210: set id with illegal value ( < 0) throws IllegalArgumentException
	 * with message "invalid id (negative)." Id remains unchanged.
	 */
	@Test @Order(210)
	void test210_setIdWithIllegalArguments() {
		IllegalArgumentException thrown = assertThrows(
			IllegalArgumentException.class, () -> {
				c1.setId(-1L);		// setId(id) with illegal id must throw IllegalArgumentException
		});
		assertEquals("invalid id (negative).", thrown.getMessage());	// verify exception message
		//
		assertEquals(c1.getId(), null);		// id remains unchanged, unassigned in this case
	}


	/*
	 * Test case: set id with multiple illegal values. Must throw IllegalArgument-
	 * Exception with message "invalid id (negative)." Id remains unassigned.
	 */
	@Test @Order(211)
	void test211_setIdWithIllegalArguments() {
		// multiple test values for illegal id
		List.of(-1L, -10L, -1000L, -1000000000000000000L, Long.MIN_VALUE).stream()
			.forEach(illegalId -> {
				IllegalArgumentException thrown = assertThrows(
					IllegalArgumentException.class, () -> {
						c1.setId(illegalId);		// setId(id) with illegal id
				});
				assertEquals("invalid id (negative).", thrown.getMessage());
				//
				assertEquals(c1.getId(), null);		// id remains unassigned
			});
	}


	/*
	 * Test case 220: set id only once.
	 */
	@Test @Order(220)
	void test220_setIdOnce() {
		c1.setId(3L);				// set id first time with regular value
		assertEquals(c1.getId(), 3L);
		c1.setId(6L);				// attempt to set id new regular value: 6L
		assertEquals(c1.getId(), 3L); // id remains unchanged: 3L
		c1.setId(3L);				// attempt to set initial  value
		assertEquals(c1.getId(), 3L);
	}
}
