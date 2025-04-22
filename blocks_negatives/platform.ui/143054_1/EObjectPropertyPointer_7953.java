    /**
     * Create a new BeanPropertyPointer.
     * @param parent parent pointer
     * @param beanInfo describes the target property/ies.
     */
    public EObjectPropertyPointer(NodePointer parent, JXPathEObjectInfo beanInfo) {
        super(parent);
        this.beanInfo = beanInfo;
    }
