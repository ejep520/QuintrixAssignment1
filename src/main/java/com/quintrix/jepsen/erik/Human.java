package com.quintrix.jepsen.erik;

import java.util.regex.Pattern;

public class Human extends Mammal{
	/** The name of the human */
	private String name;
	/** The human's email address */
	private String email;  
	/** Has the operator been warned about over-feeding? */
	private boolean fedWarned;
	/**
	 * The class constructor. Humans are created with their {@code fedWarned} set to false.
	 * @param name - <b>Required</b> - The name of the human.
	 */
	public Human(String name) {
		this.name = name;
		fedWarned = false;
	}
	/**
	 * The method used to feed a human.
	 * @throws Overfed Thrown only if the human has been fed and the operator has been warned that the human is full.
	 */
	public void Feed() throws Overfed {
		if (!getFed()) {
			setFed(true);
			System.out.println("Yum yum!!");
		} else if(!fedWarned) {
			System.out.println("No, seriously, I couldn't eat another bit.");
			fedWarned = true;
		} else {
			throw new Overfed();
		}
	}
	/**
	 * Gets the name of the human.
	 * @return The human's name.
	 */
	public String GetName() {
		return name;
	}
	/**
	 * Changes the human's name<br>
	 * Check local jurisdictions for any and all applicable laws.
	 * @param newName The new name to be assigned to the human.
	 */
	public void SetName(String newName) {
		name = newName;
	}
	public String GetEmail() {
		if (null == email || email.isEmpty()) {
			return "<No Address On File>";
		}
		return email;
	}
	/**
	 * Validates the email address against a Regex pattern defined in RFC5322. 
	 * @param newEmail The new email address
	 * @return True if the email address is valid and updated. Otherwise false.
	 * @see <a href="https://emailregex.com/">https://emailregex.com/</a>
	 */
	public boolean SetEmail(String newEmail) {
		final String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		if (Pattern.compile(pattern).matcher(newEmail).matches()) {
			email = newEmail;
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Human [name=" + name + ", email=" + email + "]";
	}
	/**
	 * The exception thrown when a human dies from over-feeding.
	 * @author Erik Jepsen &lt;erik&#64;jepster.com&gt;
	 * @see <a href="https://youtu.be/Z0E2lmgLF_k">Youtube</a>
	 *
	 */
	public class Overfed extends Exception {
		private static final long serialVersionUID = 1955349514623216432L;
		/**
		 * 	The exception constructor without a message.
		 */
		public Overfed() {
			super();
			System.err.printf("The human %s has died of overeating!\n", name);
		}
		/**
		 * The exception constructor that accepts an additional message from the caller.
		 * @param message The message to be sent to the user.
		 */
		public Overfed(String message) {
			super();
			System.err.printf("The human %s has died of overeating!\n", name);
			if (!message.isEmpty()) System.err.printf("Message: %s", message);
		}
	}
}
