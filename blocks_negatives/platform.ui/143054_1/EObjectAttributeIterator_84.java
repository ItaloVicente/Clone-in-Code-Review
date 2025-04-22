    /**
     * Create a new BeanAttributeIterator.
     * @param parent parent pointer
     * @param name name of this bean
     */
    public EObjectAttributeIterator(EStructuralFeatureOwnerPointer parent, QName name) {
        super(
            parent,
            (name.getPrefix() == null
                && (name.getName() == null || name.getName().equals("*")))
                ? null
                : name.toString(),
            false,
            null);
        this.parent = parent;
        includeXmlLang =
            (name.getPrefix() != null && name.getPrefix().equals("xml"))
                && (name.getName().equals("lang")
                || name.getName().equals("*"));
    }
