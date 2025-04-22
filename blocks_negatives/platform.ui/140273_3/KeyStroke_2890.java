        return Util.equals(naturalKey, castedObject.naturalKey);
    }

    /**
     * Formats this key stroke into the current default look.
     *
     * @return A string representation for this key stroke using the default
     *         look; never <code>null</code>.
     */
    public String format() {
        return KeyFormatterFactory.getDefault().format(this);
    }

    /**
     * Returns the set of modifier keys for this key stroke.
     *
     * @return the set of modifier keys. This set may be empty, but is
     *         guaranteed not to be <code>null</code>. If this set is not
     *         empty, it is guaranteed to only contain instances of <code>ModifierKey</code>.
     */
    public Set getModifierKeys() {
        return Collections.unmodifiableSet(modifierKeys);
    }

    /**
     * Returns the natural key for this key stroke.
     *
     * @return the natural key. May be <code>null</code>.
     */
    public NaturalKey getNaturalKey() {
        return naturalKey;
    }

    @Override
