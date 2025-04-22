    /**
     * Set the values of the initialString and the decorator
     * and object that are going to be used to determine the
     * result.
     * @param initialImage
     * @param object
     * @param definition
     */
    void setValues(Image initialImage, Object object,
            FullDecoratorDefinition definition) {
        setValues(object, definition);
        start = initialImage;
        result = null;
    }
