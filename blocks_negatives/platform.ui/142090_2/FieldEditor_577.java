        fireValueChanged(property, oldValue ? Boolean.TRUE : Boolean.FALSE, newValue ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Informs this field editor's listener, if it has one, about a change to
     * one of this field editor's properties.
     *
     * @param property the field editor property name,
     *   such as <code>VALUE</code> or <code>IS_VALID</code>
     * @param oldValue the old value object, or <code>null</code>
     * @param newValue the new value, or <code>null</code>
     */
    protected void fireValueChanged(String property, Object oldValue,
            Object newValue) {
        if (propertyChangeListener == null) {
