    /**
     * Returns the current contents of the types entry field, or its set
     * initial value if it does not exist yet (which could be <code>null</code>).
     *
     * @return the types entry field's current value or anticipated initial value,
     *   or <code>null</code>
     */
    public String getTypesFieldValue() {
        if (typesToExportField == null) {
