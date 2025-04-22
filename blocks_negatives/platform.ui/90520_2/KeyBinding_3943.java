/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.bindings.keys;

/**
 * <p>
 * A facilitiy for converting the formal representation for key strokes
 * (i.e., used in persistence) into real key stroke instances.
 * </p>
 *
 * @since 3.1
 */
public interface IKeyLookup {
	/**
	 * The formal name of the 'Alt' key.
	 */

	/**
	 * The formal name of the 'Arrow Down' key.
	 */

	/**
	 * The formal name of the 'Arrow Left' key.
	 */

	/**
	 * The formal name of the 'Arrow Right' key.
	 */

	/**
	 * The formal name of the 'Arrow Up' key.
	 */

	/**
	 * An alternate name for the backspace key.
	 */

	/**
	 * The formal name for the 'Break' key.
	 */

	/**
	 * The formal name of the backspace key.
	 */

	/**
	 * The formal name for the 'Caps Lock' key.
	 */

	/**
	 * The formal name of the 'Command' key.
	 */

	/**
	 * The formal name of the carriage return (U+000D)
	 */

	/**
	 * The formal name of the 'Ctrl' key.
	 */

	/**
	 * The formal name of the delete (U+007F) key
	 */

	/**
	 * An alternative name for the delete key.
	 */

	/**
	 * The formal name of the 'End' key.
	 */

	/**
	 * An alternative name for the enter key.
	 */

	/**
	 * The formal name of the escape (U+001B) key.
	 */

	/**
	 * An alternative name for the escape key.
	 */

	/**
	 * The formal name of the 'F1' key.
	 */

	/**
	 * The formal name of the 'F10' key.
	 */

	/**
	 * The formal name of the 'F11' key.
	 */

	/**
	 * The formal name of the 'F12' key.
	 */

	/**
	 * The formal name of the 'F13' key.
	 */

	/**
	 * The formal name of the 'F14' key.
	 */

	/**
	 * The formal name of the 'F15' key.
	 */

	/**
	 * The formal name of the 'F16' key.
	 *
	 * @since 3.6
	 */

	/**
	 * The formal name of the 'F17' key.
	 *
	 * @since 3.6
	 */

	/**
	 * The formal name of the 'F18' key.
	 *
	 * @since 3.6
	 */

	/**
	 * The formal name of the 'F19' key.
	 *
	 * @since 3.6
	 */

	/**
	 * The formal name of the 'F20' key.
	 *
	 * @since 3.6
	 */

	/**
	 * The formal name of the 'F2' key.
	 */

	/**
	 * The formal name of the 'F3' key.
	 */

	/**
	 * The formal name of the 'F4' key.
	 */

	/**
	 * The formal name of the 'F5' key.
	 */

	/**
	 * The formal name of the 'F6' key.
	 */

	/**
	 * The formal name of the 'F7' key.
	 */

	/**
	 * The formal name of the 'F8' key.
	 */

	/**
	 * The formal name of the 'F9' key.
	 */

	/**
	 * The formal name of the form feed (U+000C) key.
	 */

	/**
	 * The formal name of the 'Home' key.
	 */

	/**
	 * The formal name of the 'Insert' key.
	 */

	/**
	 * The formal name of the line feed (U+000A) key.
	 */

	/**
	 * The formal name of the 'M1' key.
	 */

	/**
	 * The formal name of the 'M2' key.
	 */

	/**
	 * The formal name of the 'M3' key.
	 */

	/**
	 * The formal name of the 'M4' key.
	 */

	/**
	 * The formal name of the null (U+0000) key.
	 */

	/**
	 * The formal name of the 'NumLock' key.
	 */

	/**
	 * The formal name of the '0' key on the numpad.
	 */

	/**
	 * The formal name of the '1' key on the numpad.
	 */

	/**
	 * The formal name of the '2' key on the numpad.
	 */

	/**
	 * The formal name of the '3' key on the numpad.
	 */

	/**
	 * The formal name of the '4' key on the numpad.
	 */

	/**
	 * The formal name of the '5' key on the numpad.
	 */

	/**
	 * The formal name of the '6' key on the numpad.
	 */

	/**
	 * The formal name of the '7' key on the numpad.
	 */

	/**
	 * The formal name of the '8' key on the numpad.
	 */

	/**
	 * The formal name of the '9' key on the numpad.
	 */

	/**
	 * The formal name of the 'Add' key on the numpad.
	 */

	/**
	 * The formal name of the 'Decimal' key on the numpad.
	 */

	/**
	 * The formal name of the 'Divide' key on the numpad.
	 */

	/**
	 * The formal name of the 'Enter' key on the numpad.
	 */

	/**
	 * The formal name of the '=' key on the numpad.
	 */

	/**
	 * The formal name of the 'Multiply' key on the numpad.
	 */

	/**
	 * The formal name of the 'Subtract' key on the numpad.
	 */

	/**
	 * The formal name of the 'Page Down' key.
	 */

	/**
	 * The formal name of the 'Page Up' key.
	 */

	/**
	 * The formal name for the 'Pause' key.
	 */

	/**
	 * The formal name for the 'Print Screen' key.
	 */

	/**
	 * An alternative name for the enter key.
	 */

	/**
	 * The formal name for the 'Scroll Lock' key.
	 */

	/**
	 * The formal name of the 'Shift' key.
	 */

	/**
	 * The formal name of the space (U+0020) key.
	 */

	/**
	 * The formal name of the tab (U+0009) key.
	 */

	/**
	 * The formal name of the vertical tab (U+000B) key.
	 */

	/**
	 * Looks up a single natural key by its formal name, and returns the integer
	 * representation for this natural key
	 *
	 * @param name
	 *            The formal name of the natural key to look-up; must not be
	 *            <code>null</code>.
	 * @return The integer representation of this key. If the natural key cannot
	 *         be found, then this method returns <code>0</code>.
	 */
	public int formalKeyLookup(String name);

	/**
	 * Looks up a single natural key by its formal name, and returns the integer
	 * representation for this natural key
	 *
	 * @param name
	 *            The formal name of the natural key to look-up; must not be
	 *            <code>null</code>.
	 * @return The integer representation of this key. If the natural key cannot
	 *         be found, then this method returns <code>0</code>.
	 */
	public Integer formalKeyLookupInteger(String name);

	/**
	 * Looks up a single modifier key by its formal name, and returns the integer
	 * representation for this modifier key
	 *
	 * @param name
	 *            The formal name of the modifier key to look-up; must not be
	 *            <code>null</code>.
	 * @return The integer representation of this key. If the modifier key
	 *         cannot be found, then this method returns <code>0</code>.
	 */
	public int formalModifierLookup(String name);

	/**
	 * Looks up a key value, and returns the formal string representation for
	 * that key
	 *
	 * @param key
	 *            The key to look-up.
	 * @return The formal string representation of this key. If this key cannot
	 *         be found, then it is simply the character corresponding to that
	 *         integer value.
	 */
	public String formalNameLookup(int key);

	/**
	 * Returns the integer representation of the ALT key.
	 *
	 * @return The ALT key
	 */
	public int getAlt();

	/**
	 * Returns the integer representation of the COMMAND key.
	 *
	 * @return The COMMAND key
	 */
	public int getCommand();

	/**
	 * Returns the integer representation of the CTRL key.
	 *
	 * @return The CTRL key
	 */
	public int getCtrl();

	/**
	 * Returns the integer representation of the SHIFT key.
	 *
	 * @return The SHIFT key
	 */
	public int getShift();

	/**
	 * Returns whether the given key is a modifier key.
	 *
	 * @param key
	 *            The integer value of the key to check.
	 * @return <code>true</code> if the key is one of the modifier keys;
	 *         <code>false</code> otherwise.
	 */
	public boolean isModifierKey(int key);
}
