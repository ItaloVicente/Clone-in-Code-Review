    }

    /**
     * Creates a structured selection from the given elements.
     * The given element array must not be <code>null</code>.
     *
     * @param elements an array of elements
     */
    public StructuredSelection(Object[] elements) {
    	Assert.isNotNull(elements);
        this.elements = new Object[elements.length];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
