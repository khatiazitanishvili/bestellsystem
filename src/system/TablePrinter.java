package system;

import java.io.PrintStream;





/**
 * Interface of configurable TablePrinter.
 * 
 * TablePrinter allows column-based output of rows into a table.
 * Column configuration is achieved by a builder during instantiation
 * where columns can be defined specified by width, borders and alignment.
 * 
 * <pre>
 * Example: configuration of orderTable with builder:
 * TablePrinter orderTable = createTablePrinter(sb, builder {@code -> } builder
 *   // builder defining table columns with width and alignment (R: right aligned)
 *   .column("|",  11)	// "Bestell-ID"
 *   .column("|",  28)	// "Bestellungen", descriptions
 *   .column("R",   7)	// "MwSt", VAT tax for each item
 *   .column(" ",   1)	// " ", marker (*) for reduced VAT tax rate
 *   .column("R",  10)	// "Preis", price for each item
 *   .column("|R", 10)	// "MwSt", VAT tax for whole order
 *   .column(" |R",12)	// "Gesamt", price for whole order
 * );
 * </pre>
 * {@code orderTable} can be used to fill in columns.
 * <pre>
 * Example: orderTable with two orders:
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
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface TablePrinter {

	/**
	 * Builder interface for constructing TablePrinter instances.
	 */
	interface Builder {		// to define table columns
		Builder column(String spec, int width);
	}

	/**
	 * Insert horizontal line into table.
	 * 
	 * @return chainable self-reference.
	 */
	TablePrinter line();

	/**
	 * Insert line spec pattern "  |.|+| |" with "|" vertical bar and
	 * fill characters.
	 * <pre>
	 * |.....|+++++|       |
	 * </pre>
	 * 
	 * @param spec spec String.
	 * @return chainable self-reference.
	 */
	TablePrinter line(String spec);

	/**
	 * Insert content line with arg[i] for column[i].
	 * 
	 * @param args content line as varargs with arg[i] for column[i].
	 * @return chainable self-reference.
	 */
	TablePrinter row(String... args);

	/**
	 * Output table to PrintStream.
	 * 
	 * @param ps output destination.
	 */
	void print(PrintStream ps);
}
