    /**
     * Returns whether this property descriptor and the given one are compatible.
     * <p>
     * The property sheet uses this method during multiple selection to determine
     * whether two property descriptors with the same id are in fact the same
     * property and can be displayed as a single entry in the property sheet.
     * </p>
     *
     * @param anotherProperty the other property descriptor
     * @return <code>true</code> if the property descriptors are compatible, and
     *   <code>false</code> otherwise
     */
    public boolean isCompatibleWith(IPropertyDescriptor anotherProperty);
