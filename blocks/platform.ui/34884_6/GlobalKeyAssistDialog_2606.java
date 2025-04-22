
package org.eclipse.ui.internal.keys;

import java.util.Comparator;

import org.eclipse.ui.keys.Key;
import org.eclipse.ui.keys.KeySequence;
import org.eclipse.ui.keys.KeyStroke;

public class FormalKeyFormatter extends AbstractKeyFormatter {

    private static final Comparator FORMAL_MODIFIER_KEY_COMPARATOR = new AlphabeticModifierKeyComparator();

    @Override
	public String format(Key key) {
        return key.toString();
    }

    @Override
	protected String getKeyDelimiter() {
        return KeyStroke.KEY_DELIMITER;
    }

    @Override
	protected String getKeyStrokeDelimiter() {
        return KeySequence.KEY_STROKE_DELIMITER;
    }

    @Override
	protected Comparator getModifierKeyComparator() {
        return FORMAL_MODIFIER_KEY_COMPARATOR;
    }

}
