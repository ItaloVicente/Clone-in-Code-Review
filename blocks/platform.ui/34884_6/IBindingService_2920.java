
package org.eclipse.ui.keys;

import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.jface.bindings.keys.IKeyLookup;
import org.eclipse.jface.bindings.keys.KeyLookupFactory;

@Deprecated
public final class CharacterKey extends NaturalKey {

	static SortedMap characterKeysByName = new TreeMap();

	public final static CharacterKey BS;

	public final static CharacterKey CR;

	public final static CharacterKey DEL;

	public final static CharacterKey ESC;

	public final static CharacterKey FF;

	public final static CharacterKey LF;

	public final static CharacterKey NUL;

	public final static CharacterKey SPACE;

	public final static CharacterKey TAB;

	public final static CharacterKey VT;

	public static final CharacterKey getInstance(final char character) {
		return new CharacterKey(character);
	}

	static {
		final IKeyLookup lookup = KeyLookupFactory.getDefault();
		BS = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.BS_NAME));
		CR = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.CR_NAME));
		DEL = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.DEL_NAME));
		ESC = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.ESC_NAME));
		FF = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.FF_NAME));
		LF = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.LF_NAME));
		NUL = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.NUL_NAME));
		SPACE = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.SPACE_NAME));
		TAB = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.TAB_NAME));
		VT = new CharacterKey(lookup.formalKeyLookup(IKeyLookup.VT_NAME));

		characterKeysByName.put(IKeyLookup.BS_NAME, CharacterKey.BS);
		characterKeysByName.put(IKeyLookup.BACKSPACE_NAME, CharacterKey.BS);
		characterKeysByName.put(IKeyLookup.CR_NAME, CharacterKey.CR);
		characterKeysByName.put(IKeyLookup.ENTER_NAME, CharacterKey.CR);
		characterKeysByName.put(IKeyLookup.RETURN_NAME, CharacterKey.CR);
		characterKeysByName.put(IKeyLookup.DEL_NAME, CharacterKey.DEL);
		characterKeysByName.put(IKeyLookup.DELETE_NAME, CharacterKey.DEL);
		characterKeysByName.put(IKeyLookup.ESC_NAME, CharacterKey.ESC);
		characterKeysByName.put(IKeyLookup.ESCAPE_NAME, CharacterKey.ESC);
		characterKeysByName.put(IKeyLookup.FF_NAME, CharacterKey.FF);
		characterKeysByName.put(IKeyLookup.LF_NAME, CharacterKey.LF);
		characterKeysByName.put(IKeyLookup.NUL_NAME, CharacterKey.NUL);
		characterKeysByName.put(IKeyLookup.SPACE_NAME, CharacterKey.SPACE);
		characterKeysByName.put(IKeyLookup.TAB_NAME, CharacterKey.TAB);
		characterKeysByName.put(IKeyLookup.VT_NAME, CharacterKey.VT);
	}

	private CharacterKey(final int key) {
		super(key);
	}

	public final char getCharacter() {
		return (char) key;
	}
}
