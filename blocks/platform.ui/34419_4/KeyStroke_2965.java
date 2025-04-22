
package org.eclipse.ui.keys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.ui.internal.util.Util;

@Deprecated
public final class KeySequence implements Comparable {

    public final static String KEY_STROKE_DELIMITER = "\u0020"; //$NON-NLS-1$

    private final static KeySequence EMPTY_KEY_SEQUENCE = new KeySequence(
            Collections.EMPTY_LIST);

    private final static int HASH_FACTOR = 89;

    private final static int HASH_INITIAL = KeySequence.class.getName()
            .hashCode();

    public final static String KEY_STROKE_DELIMITERS = KEY_STROKE_DELIMITER
            + "\b\r\u007F\u001B\f\n\0\t\u000B"; //$NON-NLS-1$

    public static KeySequence getInstance() {
        return EMPTY_KEY_SEQUENCE;
    }

    public static KeySequence getInstance(KeySequence keySequence,
            KeyStroke keyStroke) {
        if (keySequence == null || keyStroke == null) {
			throw new NullPointerException();
		}

        List keyStrokes = new ArrayList(keySequence.getKeyStrokes());
        keyStrokes.add(keyStroke);
        return new KeySequence(keyStrokes);
    }

    public static KeySequence getInstance(KeyStroke keyStroke) {
        return new KeySequence(Collections.singletonList(keyStroke));
    }

    public static KeySequence getInstance(KeyStroke[] keyStrokes) {
        return new KeySequence(Arrays.asList(keyStrokes));
    }

    public static KeySequence getInstance(List keyStrokes) {
        return new KeySequence(keyStrokes);
    }
	
	public static final KeySequence getInstance(
			final org.eclipse.jface.bindings.keys.KeySequence newKeySequence) {
		final org.eclipse.jface.bindings.keys.KeyStroke[] newKeyStrokes = newKeySequence
				.getKeyStrokes();
		final int newKeyStrokesCount = newKeyStrokes.length;
		final List legacyKeyStrokes = new ArrayList(newKeyStrokesCount);

		for (int i = 0; i < newKeyStrokesCount; i++) {
			final org.eclipse.jface.bindings.keys.KeyStroke newKeyStroke = newKeyStrokes[i];
			legacyKeyStrokes.add(SWTKeySupport
					.convertAcceleratorToKeyStroke(newKeyStroke
							.getModifierKeys()
							| newKeyStroke.getNaturalKey()));
		}
		
		return new KeySequence(legacyKeyStrokes);
	}

    public static KeySequence getInstance(String string) throws ParseException {
        if (string == null) {
			throw new NullPointerException();
		}

        List keyStrokes = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(string,
                KEY_STROKE_DELIMITERS);

        while (stringTokenizer.hasMoreTokens()) {
			keyStrokes.add(KeyStroke.getInstance(stringTokenizer.nextToken()));
		}

        try {
            return new KeySequence(keyStrokes);
        } catch (Throwable t) {
            throw new ParseException(
                    "Could not construct key sequence with these key strokes: " //$NON-NLS-1$
                            + keyStrokes);
        }
    }

    private transient int hashCode;

    private transient boolean hashCodeComputed;

    private List keyStrokes;

    private KeySequence(List keyStrokes) {
        this.keyStrokes = Util.safeCopy(keyStrokes, KeyStroke.class);

        for (int i = 0; i < this.keyStrokes.size() - 1; i++) {
            KeyStroke keyStroke = (KeyStroke) this.keyStrokes.get(i);

            if (!keyStroke.isComplete()) {
				throw new IllegalArgumentException();
			}
        }
    }

    @Override
	public int compareTo(Object object) {
        KeySequence castedObject = (KeySequence) object;
        int compareTo = Util.compare(keyStrokes, castedObject.keyStrokes);
        return compareTo;
    }

    public boolean endsWith(KeySequence keySequence, boolean equals) {
        if (keySequence == null) {
			throw new NullPointerException();
		}

        return Util.endsWith(keyStrokes, keySequence.keyStrokes, equals);
    }

    @Override
	public boolean equals(Object object) {
        if (!(object instanceof KeySequence)) {
			return false;
		}

        return keyStrokes.equals(((KeySequence) object).keyStrokes);
    }

    public String format() {
        return KeyFormatterFactory.getDefault().format(this);
    }

    public List getKeyStrokes() {
        return keyStrokes;
    }

    @Override
	public int hashCode() {
        if (!hashCodeComputed) {
            hashCode = HASH_INITIAL;
            hashCode = hashCode * HASH_FACTOR + keyStrokes.hashCode();
            hashCodeComputed = true;
        }

        return hashCode;
    }

    public boolean isComplete() {
        return keyStrokes.isEmpty()
                || ((KeyStroke) keyStrokes.get(keyStrokes.size() - 1))
                        .isComplete();
    }

    public boolean isEmpty() {
        return keyStrokes.isEmpty();
    }

    public boolean startsWith(KeySequence keySequence, boolean equals) {
        if (keySequence == null) {
			throw new NullPointerException();
		}

        return Util.startsWith(keyStrokes, keySequence.keyStrokes, equals);
    }

    @Override
	public String toString() {
        return KeyFormatterFactory.getFormalKeyFormatter().format(this);
    }
}
