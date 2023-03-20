package system.impl;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import datamodel.Article;
import datamodel.Currency;
import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;
import system.Calculator;
import system.Formatter;
import system.Printer;
import system.TablePrinter;
import system.TablePrinter.Builder;



class PrinterImpl implements Printer {
	//
	private final Calculator calculator;
	private final Formatter formatter;

	PrinterImpl(Calculator calculator, Formatter formatter) {
		this.calculator = calculator;
		this.formatter = formatter;
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
	
	

	@Override
	public TablePrinter createTablePrinter(StringBuffer sb, Consumer<Builder> builder) {
		return new TablePrinterImpl(sb, builder);
	}

	@Override
	public StringBuffer printCustomer(StringBuffer sb, Customer c) {
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
			.append(String.format("| %-31s", formatter.fmtName(c.getFirstName(), c.getLastName(), nameStyle)))
			.append(String.format("| %-44s ", contacts))
			.append("|\n");
	}


	@Override
	public StringBuffer printCustomers(StringBuffer sb, Collection<Customer> customers) {
		if(customers==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;


		return process(sb_, customers, s -> s, c -> printCustomer(sb_, c));	// calling generic print method
	}

	@Override
	public StringBuffer printArticle(StringBuffer sb, Article a) {
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

	@Override
	public StringBuffer printArticles(StringBuffer sb, Collection<Article> articles) {
		if(articles==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		return process(sb_, articles, a -> printArticle(sb_, a));
	}
	
	
		@Override
	public StringBuffer printOrder(StringBuffer sb, Order order) {
			if(order==null)
				return sb;
			//
			final String creationDate = formatter.fmtDate(order.getCreationDate(), 0, "");
			final Customer c = order.getCustomer();
			return (sb==null? new StringBuffer() : sb)
				.append(String.format("| %10s ", order.getId()))
				.append(String.format("| %-27s", formatter.fmtName(c.getFirstName(), c.getLastName(), 0)))
				.append(String.format("| %1d items ", order.itemsCount()))
				.append(String.format("| created: %s ", creationDate))
				.append("|\n");
		}
		

	@Override
	public StringBuffer printOrders(StringBuffer sb, Collection<Order> orders) {
		if(orders==null)
			return sb;
		//
		final StringBuffer sb_ = sb==null? new StringBuffer() : sb;
		return process(sb_, orders, a -> printOrder(sb_, a));
	}
	
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
	
	final Map<Currency, String> CurrencySymbol = Map.of(
			Currency.EUR, "\u20ac",		// Unicode: EURO
			Currency.USD, "$",			// ASCII: US Dollar
			Currency.GBP, "\u00A3",		// Unicode: UK Pound Sterling
			Currency.YEN, "\u00A5",		// Unicode: Japanese Yen
			Currency.BTC, "BTC"			// no Unicode for Bitcoin
		);


		final String EUR = CurrencySymbol.get(Currency.EUR);



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
	
	private final Map<TAX, Double> taxRateMapper = Map.of(
			TAX.TAXFREE,			 0.0,	// tax free rate
			TAX.GER_VAT,			19.0,	// German VAT tax (MwSt) 19.0%
			TAX.GER_VAT_REDUCED,	 7.0	// German reduced VAT tax (MwSt) 7.0%
		);
	
	public double getTaxRate(final TAX taxRate) {
		return taxRate != null? taxRateMapper.get(taxRate) : 0.0;
	}

	
	public long calculateIncludedVAT(final long grossValue, final TAX tax) {
		/*
		 * TODO: E1(2) implement formula to calculate included VAT tax.
		 */
		
		double taxrate = getTaxRate(tax);
		double calc = grossValue / (100+taxrate)*taxrate;
		long vat = Math.round(calc);
		return vat;
	}


	@Override
	public TablePrinter printOrder(TablePrinter orderTable, Order order) {
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
	@Override
	public TablePrinter printOrders(TablePrinter orderTable, Collection<Order> orders) {
		long[] totals = {	// tuple with compounded price and VAT tax values over all orders.
				0L,		// compounded order value stored in first element
				0L		// compounded order VAT tax stored in second element
		};
		
		long[] totals2;
		for(Order order : orders) {
			totals2 =calculator.calculateValueAndTax(order);
			totals[0] += totals2[0];
			totals[1] += totals2[1];
			printOrder(orderTable, order).line();
		}

		String totalPrice = fmtPrice(totals[0], 1);
		String totalVAT = fmtPrice(totals[1], 1);
		//
		process(null, null, null, null);
		
		return orderTable
			.row( "@ >        |   |", "", "", "", "", "Gesamt:", totalVAT, totalPrice)
			.line("@          +=+=+");
	}

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
