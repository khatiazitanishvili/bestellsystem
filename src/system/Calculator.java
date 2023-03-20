package system;

import java.util.Map;

import datamodel.Currency;
import datamodel.Order;
import datamodel.TAX;


/**
 * Interface to calculate values.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface Calculator {

	/**
	 * Get percent tax rate from enum value.
	 * 
	 * @param taxRate enum value of applicable tax rate.
	 * @return tax rate in percent.
	 */
	double getTaxRate(TAX taxRate);

	/**
	 * Calculate included VAT tax from gross price/value based on specific
	 * tax rate (VAT is value-added tax, in Germany it is called
	 * <i>"Mehrwertsteuer" (MwSt.)</i>).
	 * 
	 * @param grossValue value that included tax.
	 * @param tax applicable tax rate.
	 * @return tax included in gross value based on tax rate.
	 */
	long calculateIncludedVAT(long grossValue, TAX tax);

	/**
	 * Calculate compounded value and VAT tax over all order items.
	 * 
	 * @param order order to calculate compounded value and VAT tax.
	 * @return tuple with compounded value and VAT tax of order items.
	 */
	public long[] calculateValueAndTax(Order order);
	
	final Map<Currency, String> CurrencySymbol = Map.of();
}
