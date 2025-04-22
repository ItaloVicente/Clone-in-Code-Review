        return new EObjectAttributeIterator(this, name);
    }

    /**
     * Create a new PropertyOwnerPointer.
     * @param parent parent pointer
     * @param locale Locale
     */
    protected EStructuralFeatureOwnerPointer(NodePointer parent, Locale locale) {
        super(parent, locale);
    }

    /**
     * Create a new PropertyOwnerPointer.
     * @param parent pointer
     */
    protected EStructuralFeatureOwnerPointer(NodePointer parent) {
        super(parent);
    }

    @Override
