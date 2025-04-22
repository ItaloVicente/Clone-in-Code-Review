
package org.eclipse.ui.keys;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.eclipse.ui.internal.util.Util;

@Deprecated
public final class KeyStroke implements Comparable {

    public final static String KEY_DELIMITER = "\u002B"; //$NON-NLS-1$

    private final static int HASH_FACTOR = 89;

    private final static int HASH_INITIAL = KeyStroke.class.getName()
            .hashCode();

    public final static String KEY_DELIMITERS = KEY_DELIMITER;

    public static KeyStroke getInstance(ModifierKey modifierKey,
            NaturalKey naturalKey) {
        if (modifierKey == null) {
			throw new NullPointerException();
		}

        return new KeyStroke(
                new TreeSet(Collections.singletonList(modifierKey)), naturalKey);
    }

    public static KeyStroke getInstance(ModifierKey[] modifierKeys,
            NaturalKey naturalKey) {
        Util.assertInstance(modifierKeys, ModifierKey.class);
        return new KeyStroke(new TreeSet(Arrays.asList(modifierKeys)),
                naturalKey);
    }

    public static KeyStroke getInstance(NaturalKey naturalKey) {
        return new KeyStroke(Util.EMPTY_SORTED_SET, naturalKey);
    }

    public static KeyStroke getInstance(SortedSet modifierKeys,
            NaturalKey naturalKey) {
        return new KeyStroke(modifierKeys, naturalKey);
    }

    public static KeyStroke getInstance(String string) throws ParseException {
        if (string == null) {
			throw new NullPointerException();
		}

        SortedSet modifierKeys = new TreeSet();
        NaturalKey naturalKey = null;
        StringTokenizer stringTokenizer = new StringTokenizer(string,
                KEY_DELIMITERS, true);
        int i = 0;

        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();

            if (i % 2 == 0) {
                if (stringTokenizer.hasMoreTokens()) {
					token = token.toUpperCase(Locale.ENGLISH);
                    ModifierKey modifierKey = (ModifierKey) ModifierKey.modifierKeysByName
                            .get(token);

                    if (modifierKey == null || !modifierKeys.add(modifierKey)) {
						throw new ParseException(
                                "Cannot create key stroke with duplicate or non-existent modifier key: " //$NON-NLS-1$
                                        + token);
					}
                } else if (token.length() == 1) {
                    naturalKey = CharacterKey.getInstance(token.charAt(0));
                    break;
                } else {
					token = token.toUpperCase(Locale.ENGLISH);
                    naturalKey = (NaturalKey) CharacterKey.characterKeysByName
                            .get(token);

                    if (naturalKey == null) {
						naturalKey = (NaturalKey) SpecialKey.specialKeysByName
                                .get(token);
					}

                    if (naturalKey == null) {
						throw new ParseException(
                                "Cannot create key stroke with invalid natural key: " //$NON-NLS-1$
                                        + token);
					}
                }
            }

            i++;
        }

        try {
            return new KeyStroke(modifierKeys, naturalKey);
        } catch (Throwable t) {
            throw new ParseException("Cannot create key stroke with " //$NON-NLS-1$
                    + modifierKeys + " and " + naturalKey); //$NON-NLS-1$
        }
    }

    private transient int hashCode;

    private transient boolean hashCodeComputed;

    private SortedSet modifierKeys;

    private transient ModifierKey[] modifierKeysAsArray;

    private NaturalKey naturalKey;

    private KeyStroke(SortedSet modifierKeys, NaturalKey naturalKey) {
        this.modifierKeys = Util.safeCopy(modifierKeys, ModifierKey.class);
        this.naturalKey = naturalKey;
        this.modifierKeysAsArray = (ModifierKey[]) this.modifierKeys
                .toArray(new ModifierKey[this.modifierKeys.size()]);
    }

    @Override
	public int compareTo(Object object) {
        KeyStroke castedObject = (KeyStroke) object;
        int compareTo = Util.compare(modifierKeysAsArray,
                castedObject.modifierKeysAsArray);

        if (compareTo == 0) {
			compareTo = Util.compare(naturalKey, castedObject.naturalKey);
		}

        return compareTo;
    }

    @Override
	public boolean equals(Object object) {
        if (!(object instanceof KeyStroke)) {
			return false;
		}

        KeyStroke castedObject = (KeyStroke) object;
        
        if (!modifierKeys.equals(castedObject.modifierKeys)) {
			return false;
		}
        return Util.equals(naturalKey, castedObject.naturalKey);
    }

    public String format() {
        return KeyFormatterFactory.getDefault().format(this);
    }

    public Set getModifierKeys() {
        return Collections.unmodifiableSet(modifierKeys);
    }

    public NaturalKey getNaturalKey() {
        return naturalKey;
    }

    @Override
	public int hashCode() {
        if (!hashCodeComputed) {
            hashCode = HASH_INITIAL;
            hashCode = hashCode * HASH_FACTOR + modifierKeys.hashCode();
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(naturalKey);
            hashCodeComputed = true;
        }

        return hashCode;
    }

    public boolean isComplete() {
        return naturalKey != null;
    }

    @Override
	public String toString() {
        return KeyFormatterFactory.getFormalKeyFormatter().format(this);
    }
}
