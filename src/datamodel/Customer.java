package datamodel;

import java.util.*;

/**
 * Class for entity type <i>Customer</i>.
 * <p>
 * Customer is an individual (a person, not a business) who creates and holds (owns) orders in the system.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Customer {

    /**
     * Unique Customer id attribute, {@code id < 0} is invalid, id can only be set once.
     */
    private long id = -1;

    /**
     * Customer's surname attribute, never null.
     */
    private String lastName = "";

    /**
     * None-surname name parts, never null.
     */
    private String firstName = "";

    /**
     * Customer contact information with multiple contacts.
     */
    private final List<String> contacts;

    /**
     * Default constructor.
     */
    public Customer() {
		contacts = new ArrayList<String>();
    }

    /**
     * Constructor with single-String name argument.
     * @param name single-String Customer name, e.g. "Eric Meyer".
     * @throws IllegalArgumentException if name argument is null.
     */
    public Customer(String name) throws IllegalArgumentException {
    	if(name == "") {
    		throw new IllegalArgumentException("name empty.");
    	}
    	if(name == null) {
    		throw new IllegalArgumentException("name null.");
    	}
    	else {
    		setName(name);
    		this.contacts = new ArrayList<>();
    	}
    }


    /**
     * Id getter.
     * @return customer id, returns {@code null}, if id is unassigned.
     */
    public Long getId() {
    	if(this.id != -1) {
    		return this.id;
    	}
    	else {
    		return null;
    	}
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     * @param id value to assign if this.id attribute is still unassigned {@code id < 0} and id argument is valid.
     * @throws IllegalArgumentException if id argument is invalid ({@code id < 0}).
     * @return chainable self-reference.
     */
    public final Customer setId(long id) throws IllegalArgumentException {
    	if(id >= 0 && this.id == -1) {
    		this.id = id;
    	}
    	else if(id < 0) {
    		throw new IllegalArgumentException("invalid id (negative).");
    	}
        return this;
    }

    /**
     * LastName getter.
     * @return value of lastName attribute, never null, mapped to "".
     */
    public String getLastName() {
        if(lastName != null) { return lastName; }
        return "";
    }

    /**
     * FirstName getter.
     * @return value of firstName attribute, never null, mapped to "".
     */
    public String getFirstName() {
    	if(firstName != null) { return firstName; }
        return "";
    }
    
    /**
     * name getter.
     * @return value of lastName and firstName, separated with a comma in between.
     */
    
    public String getName() {
		return String.format("%s, %s", lastName, firstName);
    }

    /**
     * Setter that splits a single-String name (for example, "Eric Meyer") into first-
     * ("Eric") and lastName ("Meyer") parts and assigns parts to corresponding attributes.
     * @param first value assigned to firstName attribute, null is ignored.
     * @param last value assigned to lastName attribute, null is ignored.
     * @return chainable self-reference.
     */
    public Customer setName(String first, String last) {
    	if(firstName != null) { firstName = first; }
    	if(lastName != null) { lastName = last; }
        return this;
    }

    /**
     * Setter that splits a single-String name (e.g. "Eric Meyer") into first- and
     * lastName parts and assigns parts to corresponding attributes.
     * @param name single-String name to split into first- and lastName parts.
     * @throws IllegalArgumentException if name argument is null.
     * @return chainable self-reference.
     */
    public Customer setName(String name) throws IllegalArgumentException{
    	if(name != null) {
    		return splitName(name); 
    	} else { 
    		throw new IllegalArgumentException(); }
    }

    /**
     * Return number of contacts.
     * @return number of contacts.
     */
    public int contactsCount() {
    	int count = 0;
    	if(!contacts.isEmpty()) {
    		count = contacts.size();
    	}
        return count;
    }

    /**
     * Contacts getter (as {@code String[]}).
     * @return contacts (as {@code String[]}).
     */
    public String[ ] getContacts() {
    	if(!contacts.isEmpty()) {
    		String[] list = new String[contacts.size()];
    		for(int i = 0; i < contacts.size(); i++) {
    			list[i] = contacts.get(i);
    		}
    		return list;
    	}
    	return new String[] {};
    }

    /**
     * Add new contact for Customer. Only valid contacts (not null, "" or duplicates) are added. 
     * @param contact valid contact(not null or "" nor duplicate), invalid contacts are ignored
     * @return chainable self-reference
     */
    public Customer addContact(String contact) throws IllegalArgumentException {
    	String[] characters = { "\t", "\n", "\"", "\'", ";", ":", ","};
    	String thowString = contact;
    	
    	if(contacts == null) {
    		contacts.add(contact);
    	}
    	
    	for(String chara : characters) {
    		if(contact.contains(chara)) {
    			contact = contact.replaceAll(chara, "");
    		}
    	}

    	if (!contacts.contains(contact)) {
    		while(contact.startsWith(" ")) {
    			contact = contact.replaceFirst(" ", "");
    		}
    		while(contact.endsWith(" ")) {
    			contact = contact.substring(0, contact.length()-1);
    		}
    		
    		if(contact.length() < 6) {
    			throw new IllegalArgumentException("contact less than 6 characters: \"" + thowString +
    					"\".");
        	}
    		contacts.add(contact);
    		return this;
    	}
    	return this;
    }

    /**
     * Delete the i-th contact with {@code i >= 0 and i < contactCount()},
     * otherwise method has no effect.
     * @param i index of contact to delete
     */
    public void deleteContact(int i) {
    	if(contacts.size() > i && i >= 0) {
    		contacts.remove(i);
    	}
    }

    /**
     * Delete all contacts.
     */
    public void deleteAllContacts() {
        contacts.clear();
    }

    /**
     * Split single-String name into last- and first name parts.
     * @param name single-String name to split into first- and last name parts.
     * @return chainable self-reference.
     */
	private Customer splitName(String name) {
		String[] fullname;
		if (name.contains(",")) {
			fullname = name.split(", ");
			lastName = fullname[0];
			firstName = fullname[1];
			return this;
		}
		if (name.contains(";")) {
			fullname = name.split("; ");
			lastName = fullname[0];
			firstName = fullname[1];
			return this;
		}
		if (name.contains(" ")) {
			fullname = name.split(" ");
			for (int i = 0; i < fullname.length; i++) {
				if(i == 0) { firstName = fullname[i]; }
				if (i < fullname.length - 1 && i > 0) { firstName += " " + fullname[i];} 
				else { lastName = fullname[i];}
			}
		}
		
		else {
			lastName = name;
		}
		return this;
	}
}