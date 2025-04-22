    /** property index */
    protected int propertyIndex = UNSPECIFIED_PROPERTY;

    /** owning object */
    protected Object bean;

    /**
     * Takes a javabean, a descriptor of a property of that object and
     * an offset within that property (starting with 0).
     * @param parent parent pointer
     */
    public EStructuralFeaturePointer(NodePointer parent) {
        super(parent);
    }

    /**
     * Get the property index.
     * @return int index
     */
    public int getPropertyIndex() {
        return propertyIndex;
    }

    /**
     * Set the property index.
     * @param index property index
     */
    public void setPropertyIndex(int index) {
        if (propertyIndex != index) {
            propertyIndex = index;
            setIndex(WHOLE_COLLECTION);
        }
    }

    /**
     * Get the parent bean.
     * @return Object
     */
    public EObject getBean() {
        if (bean == null) {
            bean = getImmediateParentPointer().getNode();
        }
        return (EObject) bean;
    }

    @Override
