    }

    /**
     * Whether this instance is defined. A defined instance is one that has been
     * fully initialized. This allows objects to effectively disappear even
     * though other objects may still have references to them.
     *
     * @return <code>true</code> if this object is defined; <code>false</code>
     *         otherwise.
     */
    public final boolean isDefined() {
        return defined;
    }

    /**
     * The string representation of this object -- for debugging purposes only.
     * This string should not be shown to an end user.
     *
     * @return The string representation; never <code>null</code>.
     */
    @Override
