                }
            }

            i++;
        }

        try {
            return new KeyStroke(modifierKeys, naturalKey);
        } catch (Throwable t) {
        }
    }

    /**
     * The cached hash code for this object. Because <code>KeyStroke</code>
     * objects are immutable, their hash codes need only to be computed once.
     * After the first call to <code>hashCode()</code>, the computed value
     * is cached here for all subsequent calls.
     */
    private transient int hashCode;

    /**
     * A flag to determine if the <code>hashCode</code> field has already
     * been computed.
     */
    private transient boolean hashCodeComputed;

    /**
     * The set of modifier keys for this key stroke.
     */
    private SortedSet modifierKeys;

    /**
     * The set of modifier keys for this key stroke in the form of an array.
     * Used internally by <code>int compareTo(Object)</code>.
     */
    private transient ModifierKey[] modifierKeysAsArray;

    /**
     * The natural key for this key stroke.
     */
    private NaturalKey naturalKey;

    /**
     * Constructs an instance of <code>KeyStroke</code> given a set of
     * modifier keys and a natural key.
     *
     * @param modifierKeys
     *            the set of modifier keys. This set may be empty, but it must
     *            not be <code>null</code>. If this set is not empty, it
     *            must only contain instances of <code>ModifierKey</code>.
     * @param naturalKey
     *            the natural key. May be <code>null</code>.
     */
    private KeyStroke(SortedSet modifierKeys, NaturalKey naturalKey) {
        this.modifierKeys = Util.safeCopy(modifierKeys, ModifierKey.class);
        this.naturalKey = naturalKey;
        this.modifierKeysAsArray = (ModifierKey[]) this.modifierKeys
                .toArray(new ModifierKey[this.modifierKeys.size()]);
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
