package system.impl;

import java.util.Map;
import java.util.Optional;

import datamodel.Currency;
import datamodel.Order;
import datamodel.TAX;
import system.Calculator;


class CalculatorImpl implements Calculator {
	

	
	/**
	 * Applicable tax rate mapped from TAX enum.
	 */
	private final Map<TAX, Double> taxRateMapper = Map.of(
		TAX.TAXFREE,			 0.0,	// tax free rate
		TAX.GER_VAT,			19.0,	// German VAT tax (MwSt) 19.0%
		TAX.GER_VAT_REDUCED,	 7.0	// German reduced VAT tax (MwSt) 7.0%
	);

	@Override
	public double getTaxRate(TAX taxRate) {
			return taxRate != null? taxRateMapper.get(taxRate) : 0.0;
		}

	

	@Override
	public long calculateIncludedVAT(long grossValue, TAX tax) {
		double taxrate = getTaxRate(tax);
		double calc = grossValue / (100+taxrate)*taxrate;
		long vat = Math.round(calc);
		return vat;
	}

	@Override
	public long[] calculateValueAndTax(Order order) {
		long[] totals = {0L, 0L};
		Optional.ofNullable(order).ifPresent(o -> o.getItems().forEach(item -> {
			int units = item.getUnitsOrdered();
			long unitPrice = item.getArticle().getUnitPrice();
			long itemPrice = unitPrice * units;
			long vat = calculateIncludedVAT(itemPrice, item.getArticle().getTax());
			totals[0] += itemPrice;	// compound item price
			totals[1] += vat;		// compound item tax	
		}));
		return totals;	// return tuple with compounded {value, vat}
	}
}
