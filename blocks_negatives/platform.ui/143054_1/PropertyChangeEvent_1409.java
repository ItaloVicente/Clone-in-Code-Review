    /**
     * Creates a new property change event.
     *
     * @param source the object whose property has changed
     * @param property the property that has changed (must not be <code>null</code>)
     * @param oldValue the old value of the property, or <code>null</code> if none
     * @param newValue the new value of the property, or <code>null</code> if none
     */
    public PropertyChangeEvent(Object source, String property, Object oldValue,
            Object newValue) {
        super(source);
        Assert.isNotNull(property);
        this.propertyName = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
