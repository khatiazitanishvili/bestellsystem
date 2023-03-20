package datamodel;
/**
 * Java Enum Type to enumerate tax rates applicable for articles.
 * <p>
 * A business is obligated to collect taxes from sales based on the applicable tax rate.
 *  German sales tax (Umsatzsteuer) is compounded as value-added tax (VAT, Mehrwertsteuer, MwSt.)
 *  along the production and distribution chain
 *  <a href="https://de.wikipedia.org/wiki/Umsatzsteuer_(Deutschland)">Umsatzsteuer (Deutschland)</a>.
 *  </p><p>
 *  A regular rate of 19% applies to all articles. A reduced rate of 7% applies to food, books,
 *  medication and health care items. VAT is included in final prices.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public enum TAX {
    TAXFREE,
    GER_VAT,
    GER_VAT_REDUCED
}