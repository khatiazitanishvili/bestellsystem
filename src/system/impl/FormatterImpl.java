package system.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import datamodel.Currency;
import system.Formatter;


class FormatterImpl implements Formatter {
	
	
	final Map<Currency, String> CurrencySymbol = Map.of(
			Currency.EUR, "\u20ac",		// Unicode: EURO
			Currency.USD, "$",			// ASCII: US Dollar
			Currency.GBP, "\u00A3",		// Unicode: UK Pound Sterling
			Currency.YEN, "\u00A5",		// Unicode: Japanese Yen
			Currency.BTC, "BTC"			// no Unicode for Bitcoin
		);
	
	final String EUR = CurrencySymbol.get(Currency.EUR);

	
	
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
	
	private static final DateFormat[] timeFmts = {
			new SimpleDateFormat("HH:mm:ss"),				// 0: 18:55:43
			new SimpleDateFormat("HH:mm"),					// 1: 18:55
			new SimpleDateFormat("HH:mm:ss.SSS"),			// 2: 18:55:43.348
			new SimpleDateFormat("HH:mm:ss, z"),			// 3: 18:55:43, CEST
			new SimpleDateFormat("HH:mm:ss, Z"),			// 4: 18:55:43, +0200
			new SimpleDateFormat("HH:mm:ss, aa")			// 5: 06:55:43 PM
		};


	@Override
	public String fmtName(String firstName, String lastName, int... style) {
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
	

	@Override
	public String fmtDate(long datetime, int dateStyle, String fill, int... timeStyle) {
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
	
	@Override
	public String fmtPrice(long price, int... style) {
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

	@Override
	public String fmtDecimal(long value, int decimalDigits, String... unit) {
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
}
