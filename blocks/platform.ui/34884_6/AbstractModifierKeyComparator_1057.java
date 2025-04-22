
package org.eclipse.ui.internal.keys;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.IKeyFormatter;
import org.eclipse.ui.keys.Key;
import org.eclipse.ui.keys.KeySequence;
import org.eclipse.ui.keys.KeyStroke;
import org.eclipse.ui.keys.ModifierKey;
import org.eclipse.ui.keys.NaturalKey;

public abstract class AbstractKeyFormatter implements IKeyFormatter {

    protected final static String KEY_DELIMITER_KEY = "KEY_DELIMITER"; //$NON-NLS-1$

    protected final static String KEY_STROKE_DELIMITER_KEY = "KEY_STROKE_DELIMITER"; //$NON-NLS-1$

    private final static ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(AbstractKeyFormatter.class.getName());

    @Override
	public String format(Key key) {
        String name = key.toString();
        return Util.translateString(RESOURCE_BUNDLE, name, name, false, false);
    }

    @Override
	public String format(KeySequence keySequence) {
        StringBuffer stringBuffer = new StringBuffer();

        Iterator keyStrokeItr = keySequence.getKeyStrokes().iterator();
        while (keyStrokeItr.hasNext()) {
            stringBuffer.append(format((KeyStroke) keyStrokeItr.next()));

            if (keyStrokeItr.hasNext()) {
                stringBuffer.append(getKeyStrokeDelimiter());
            }
        }

        return stringBuffer.toString();
    }

    @Override
	public String format(KeyStroke keyStroke) {
        String keyDelimiter = getKeyDelimiter();

        SortedSet modifierKeys = new TreeSet(getModifierKeyComparator());
        modifierKeys.addAll(keyStroke.getModifierKeys());
        StringBuffer stringBuffer = new StringBuffer();
        Iterator modifierKeyItr = modifierKeys.iterator();
        while (modifierKeyItr.hasNext()) {
            stringBuffer.append(format((ModifierKey) modifierKeyItr.next()));
            stringBuffer.append(keyDelimiter);
        }

        NaturalKey naturalKey = keyStroke.getNaturalKey();
        if (naturalKey != null) {
            stringBuffer.append(format(naturalKey));
        }

        return stringBuffer.toString();

    }

    protected abstract String getKeyDelimiter();

    protected abstract String getKeyStrokeDelimiter();

    protected abstract Comparator getModifierKeyComparator();

}
