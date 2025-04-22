
package org.eclipse.ui.keys;

import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;
import org.eclipse.jface.util.Util;

@Deprecated
public final class ModifierKey extends Key {

	static SortedMap modifierKeysByName = new TreeMap();

	public final static ModifierKey ALT;

	public final static ModifierKey COMMAND;

	public final static ModifierKey CTRL;

	private final static String M1_NAME = "M1"; //$NON-NLS-1$	

	private final static String M2_NAME = "M2"; //$NON-NLS-1$

	private final static String M3_NAME = "M3"; //$NON-NLS-1$

	private final static String M4_NAME = "M4"; //$NON-NLS-1$	

	public final static ModifierKey SHIFT;

	static {
		final IKeyLookup lookup = KeyLookupFactory.getDefault();
		ALT = new ModifierKey(lookup.getAlt());
		COMMAND = new ModifierKey(lookup.getCommand());
		CTRL = new ModifierKey(lookup.getCtrl());
		SHIFT = new ModifierKey(lookup.getShift());
		
		modifierKeysByName.put(ModifierKey.ALT.toString(), ModifierKey.ALT);
		modifierKeysByName.put(ModifierKey.COMMAND.toString(),
				ModifierKey.COMMAND);
		modifierKeysByName.put(ModifierKey.CTRL.toString(), ModifierKey.CTRL);
		modifierKeysByName.put(ModifierKey.SHIFT.toString(), ModifierKey.SHIFT);
		modifierKeysByName
				.put(
						M1_NAME,
						Util.isMac() ? ModifierKey.COMMAND : ModifierKey.CTRL);
		modifierKeysByName.put(M2_NAME, ModifierKey.SHIFT);
		modifierKeysByName.put(M3_NAME, ModifierKey.ALT);
		modifierKeysByName
				.put(
						M4_NAME,
						Util.isMac() ? ModifierKey.CTRL : ModifierKey.COMMAND);
	}

	private ModifierKey(final int key) {
		super(key);
	}
}
