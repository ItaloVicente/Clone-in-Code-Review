
package org.eclipse.ui.internal.keys;

import java.util.Comparator;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.CharacterKey;
import org.eclipse.ui.keys.Key;
import org.eclipse.ui.keys.KeySequence;
import org.eclipse.ui.keys.KeyStroke;
import org.eclipse.ui.keys.ModifierKey;
import org.eclipse.ui.keys.SpecialKey;

public class NativeKeyFormatter extends AbstractKeyFormatter {

    private final static String CARBON_KEY_DELIMITER_KEY = "CARBON_KEY_DELIMITER"; //$NON-NLS-1$

    private final static HashMap CARBON_KEY_LOOK_UP = new HashMap();

    private final static Comparator MODIFIER_KEY_COMPARATOR = new NativeModifierKeyComparator();

    private final static ResourceBundle RESOURCE_BUNDLE;

    private final static String WIN32_KEY_STROKE_DELIMITER_KEY = "WIN32_KEY_STROKE_DELIMITER"; //$NON-NLS-1$

    static {
        RESOURCE_BUNDLE = ResourceBundle.getBundle(NativeKeyFormatter.class
                .getName());

        CARBON_KEY_LOOK_UP.put(CharacterKey.BS.toString(), "\u232B");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(CharacterKey.CR.toString(), "\u21A9");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(CharacterKey.DEL.toString(), "\u2326");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(CharacterKey.SPACE.toString(), "\u2423");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.ALT.toString(), "\u2325");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.COMMAND.toString(), "\u2318");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.CTRL.toString(), "\u2303");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(ModifierKey.SHIFT.toString(), "\u21E7");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_DOWN.toString(), "\u2193");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_LEFT.toString(), "\u2190");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_RIGHT.toString(), "\u2192");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.ARROW_UP.toString(), "\u2191");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.END.toString(), "\u2198");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.NUMPAD_ENTER.toString(), "\u2324");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.HOME.toString(), "\u2196");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.PAGE_DOWN.toString(), "\u21DF");  //$NON-NLS-1$
        CARBON_KEY_LOOK_UP.put(SpecialKey.PAGE_UP.toString(), "\u21DE");  //$NON-NLS-1$
    }

    @Override
	public String format(Key key) {
        String name = key.toString();

        if (org.eclipse.jface.util.Util.isMac()) {    	
            String formattedName = (String) CARBON_KEY_LOOK_UP.get(name);
            if (formattedName != null) {
                return formattedName;
            }
        }

        return super.format(key);
    }

    @Override
	protected String getKeyDelimiter() {
        if (org.eclipse.jface.util.Util.isMac()) {
            return Util.translateString(RESOURCE_BUNDLE,
                    CARBON_KEY_DELIMITER_KEY, Util.ZERO_LENGTH_STRING, false,
                    false);
        } else {
            return Util.translateString(RESOURCE_BUNDLE, KEY_DELIMITER_KEY,
                    KeyStroke.KEY_DELIMITER, false, false);
        }
    }

    @Override
	protected String getKeyStrokeDelimiter() {
        if (org.eclipse.jface.util.Util.isWindows()) {
            return Util.translateString(RESOURCE_BUNDLE,
                    WIN32_KEY_STROKE_DELIMITER_KEY,
                    KeySequence.KEY_STROKE_DELIMITER, false, false);
        } else {
            return Util.translateString(RESOURCE_BUNDLE,
                    KEY_STROKE_DELIMITER_KEY, KeySequence.KEY_STROKE_DELIMITER,
                    false, false);
        }
    }

    @Override
	protected Comparator getModifierKeyComparator() {
        return MODIFIER_KEY_COMPARATOR;
    }
}
