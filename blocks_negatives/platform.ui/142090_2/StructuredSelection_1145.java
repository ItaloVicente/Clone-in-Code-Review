    }

    /**
     * Creates a structured selection containing a single object.
     * The object must not be <code>null</code>.
     *
     * @param element the element
     */
    public StructuredSelection(Object element) {
        Assert.isNotNull(element);
