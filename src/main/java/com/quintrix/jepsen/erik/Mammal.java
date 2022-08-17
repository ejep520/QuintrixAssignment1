package com.quintrix.jepsen.erik;

/**
 * Mammals are a type of animal found on the planet Earth.
 * @author Erik Jepsen
 */
public class Mammal {
	/** Has this mammal been fed? */
	private boolean fed;
	/** Does this mammal have hair currently? */
	private boolean hair;
	/** All mammals have three bones in their middle ear.
	 * @see <a href="https://en.wikipedia.org/w/index.php?title=Mammal&amp;oldid=1104071482">Wikipedia</a>
	 */
	private final int earBoneCount = 3;
	public Mammal() {
		fed = false;
		hair = true;
	}
	/**
	 * Requests the number of bones found in the middle ear.
	 * @return The number of bones found in the middle ear.
	 */
	public int GetEarBoneCount() {
		return earBoneCount;
	}
	/**
	 * Does this mammal have hair currently?
	 * @return The current state of hair.
	 */
	public boolean getHair() {
		return hair;
	}
	/**
	 * Has this mammal been fed?
	 * @return The satiation state of the mammal's stomach.
	 */
	public boolean getFed() {
		return fed;
	}
	/**
	 * Sets the fed state of the mammal.
	 * @param newValue The updated fed state of the mammal.
	 */
	public void setFed(boolean newValue) {
		fed = newValue;
	}
	/**
	 * Sets a new hairiness state for the mammal.
	 * @param newValue The new hairiness state of the mammal.
	 */
	public void setHair(boolean newValue) {
		hair = newValue;
	}
}
