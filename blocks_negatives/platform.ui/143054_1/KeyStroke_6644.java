        if (!hashCodeComputed) {
            hashCode = HASH_INITIAL;
            hashCode = hashCode * HASH_FACTOR + modifierKeys.hashCode();
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(naturalKey);
            hashCodeComputed = true;
        }

        return hashCode;
    }

    /**
     * Returns whether or not this key stroke is complete. Key strokes are
     * complete iff they have a natural key which is not <code>null</code>.
     *
     * @return <code>true</code>, iff the key stroke is complete.
     */
    public boolean isComplete() {
        return naturalKey != null;
    }

    /**
     * Returns the formal string representation for this key stroke.
     *
     * @return The formal string representation for this key stroke. Guaranteed
     *         not to be <code>null</code>.
     */
    @Override
