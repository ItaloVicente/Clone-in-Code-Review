
package org.eclipse.ui.internal.keys;

import java.util.Comparator;
import java.util.ResourceBundle;

import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.Key;
import org.eclipse.ui.keys.KeySequence;
import org.eclipse.ui.keys.KeyStroke;
import org.eclipse.ui.keys.ModifierKey;

public class EmacsKeyFormatter extends AbstractKeyFormatter {

    private static final Comparator EMACS_MODIFIER_KEY_COMPARATOR = new AlphabeticModifierKeyComparator();

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(EmacsKeyFormatter.class.getName());

    @Override
	public String format(Key key) {
        if (key instanceof ModifierKey) {
            String formattedName = Util.translateString(RESOURCE_BUNDLE, key
                    .toString(), null, false, false);
            if (formattedName != null) {
                return formattedName;
            }
        }

        return super.format(key).toLowerCase();
    }

    @Override
	protected String getKeyDelimiter() {
        return Util.translateString(RESOURCE_BUNDLE, KEY_DELIMITER_KEY,
                KeyStroke.KEY_DELIMITER, false, false);
    }

    @Override
	protected String getKeyStrokeDelimiter() {
        return Util.translateString(RESOURCE_BUNDLE, KEY_STROKE_DELIMITER_KEY,
                KeySequence.KEY_STROKE_DELIMITER, false, false);
    }

    @Override
	protected Comparator getModifierKeyComparator() {
        return EMACS_MODIFIER_KEY_COMPARATOR;
    }

}
