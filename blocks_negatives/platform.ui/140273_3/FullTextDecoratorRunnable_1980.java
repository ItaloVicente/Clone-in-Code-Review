    /**
     * Set the values of the initialString and the decorator
     * and object that are going to be used to determine the
     * result.
     * @param initialString
     * @param object
     * @param definition
     */
    void setValues(String initialString, Object object,
            FullDecoratorDefinition definition) {
        setValues(object, definition);
        start = initialString;
        result = null;
    }
