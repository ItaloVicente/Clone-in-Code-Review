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
     * Defines this context by giving it a name, and possibly a description and
     * a parent identifier as well. The defined property automatically becomes
     * <code>true</code>.
     * </p>
     * <p>
     * Notification is sent to all listeners that something has changed.
     * </p>
     *
     * @param name
     *            The name of this context; must not be <code>null</code>.
     * @param description
     *            The description for this context; may be <code>null</code>.
     * @param parentId
     *            The parent identifier for this context; may be
     *            <code>null</code>.
     */
