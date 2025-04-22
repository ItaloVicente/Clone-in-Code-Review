        return new QName(null, getPropertyName());
    }

    /**
     * Get the property name.
     * @return String property name.
     */
    public abstract String getPropertyName();

    /**
     * Set the property name.
     * @param propertyName property name to set.
     */
    public abstract void setPropertyName(String propertyName);

    /**
     * Count the number of properties represented.
     * @return int
     */
    public abstract int getPropertyCount();

    /**
     * Get the names of the included properties.
     * @return String[]
     */
    public abstract String[] getPropertyNames();

    /**
     * Learn whether this pointer references an actual property.
     * @return true if actual
     */
    protected abstract boolean isActualProperty();

    @Override
