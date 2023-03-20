/**
 * Module of a simple order processing application:
 * <code>{@value application.package_info#RootName}.</code>
 * <p>
 * The {@link datamodel} package exports classes for the business information
 * (entities, relations) managed in the order processing system.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

module se1.bestellsystem {
	opens application;
	exports datamodel;

//	requires junit;
//	requires org.junit.jupiter.api;
}
