
package org.eclipse.ui.internal.keys;

import org.eclipse.ui.commands.IKeySequenceBinding;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.keys.KeySequence;

public final class KeySequenceBinding implements IKeySequenceBinding {

    public static final String DEFAULT_CONTEXT_ID = "org.eclipse.ui.contexts.window"; //$NON-NLS-1$

    private final static int HASH_FACTOR = 89;

    private final static int HASH_INITIAL = KeySequenceBinding.class.getName()
            .hashCode();

    private transient int hashCode;

    private transient boolean hashCodeComputed;

    private KeySequence keySequence;

    private int match;

    private transient String string;

    public KeySequenceBinding(KeySequence keySequence, int match) {
        if (keySequence == null) {
			throw new NullPointerException();
		}

        if (match < 0) {
			throw new IllegalArgumentException();
		}

        this.keySequence = keySequence;
        this.match = match;
    }

    @Override
	public int compareTo(Object object) {
        KeySequenceBinding castedObject = (KeySequenceBinding) object;
        int compareTo = Util.compare(match, castedObject.match);

        if (compareTo == 0) {
			compareTo = Util.compare(keySequence, castedObject.keySequence);
		}

        return compareTo;
    }

    @Override
	public boolean equals(Object object) {
        if (!(object instanceof KeySequenceBinding)) {
			return false;
		}

        final KeySequenceBinding castedObject = (KeySequenceBinding) object;
        if (!Util.equals(keySequence, castedObject.keySequence)) {
            return false;
        }
        
        return Util.equals(match, castedObject.match);
    }

    @Override
	public KeySequence getKeySequence() {
        return keySequence;
    }

    public int getMatch() {
        return match;
    }

    @Override
	public int hashCode() {
        if (!hashCodeComputed) {
            hashCode = HASH_INITIAL;
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(keySequence);
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(match);
            hashCodeComputed = true;
        }

        return hashCode;
    }

    @Override
	public String toString() {
        if (string == null) {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('[');
            stringBuffer.append(keySequence);
            stringBuffer.append(',');
            stringBuffer.append(match);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }
}
