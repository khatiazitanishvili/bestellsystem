package system;


/**
 * Interface to convert values into readable String formats.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface Formatter {

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
	public String fmtName(String firstName, String lastName, int... style);


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
	public String fmtDate(long datetime, int dateStyle, String fill, int... timeStyle);


	/**
	 * Format long value to price according to a style (0 is default):
	 * <pre>
	 * Example: long value: 499
	 * style: 0: "4.99"
	 *        1: "4.99"
	 *        2: "4.99$"
	 *        3: "4.99"
	 *        4:  "499"
	 *        5:  "499"
	 * </pre>
	 * 
	 * @param price long value as price.
	 * @param style price formatting style.
	 * @return formatted price according to style.
	 */
	public String fmtPrice(long price, int... style);


	/**
	 * Method to format a long value to a decimal String with a specified
	 * number of digits.
	 * 
	 * @param value value to format to String in decimal format.
	 * @param decimalDigits number of digits.
	 * @param unit appended unit as String.
	 * @return formatted decimal value as String.
	 */
	public String fmtDecimal(long value, int decimalDigits, String... unit);

}
