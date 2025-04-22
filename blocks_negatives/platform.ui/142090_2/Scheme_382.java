        final Scheme scheme = (Scheme) object;
        int compareTo = Util.compare(this.id, scheme.id);
        if (compareTo == 0) {
            compareTo = Util.compare(this.name, scheme.name);
            if (compareTo == 0) {
                compareTo = Util.compare(this.parentId, scheme.parentId);
                if (compareTo == 0) {
                    compareTo = Util.compare(this.description,
                            scheme.description);
                    if (compareTo == 0) {
                        compareTo = Util.compare(this.defined, scheme.defined);
                    }
                }
            }
        }

        return compareTo;
    }

    /**
     * <p>
     * Defines this scheme by giving it a name, and possibly a description and a
     * parent identifier as well. The defined property for the scheme automatically
     * becomes <code>true</code>.
     * </p>
     * <p>
     * Notification is sent to all listeners that something has changed.
     * </p>
     *
     * @param name
     *            The name of this scheme; must not be <code>null</code>.
     * @param description
     *            The description for this scheme; may be <code>null</code>.
     * @param parentId
     *            The parent identifier for this scheme; may be
     *            <code>null</code>.
     */
    public final void define(final String name, final String description,
            final String parentId) {
        if (name == null) {
            throw new NullPointerException(
        }

        final boolean definedChanged = !this.defined;
        this.defined = true;
