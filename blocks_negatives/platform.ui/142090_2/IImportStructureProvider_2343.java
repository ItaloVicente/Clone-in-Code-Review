    /**
     * Returns the contents of the specified structured element, or
     * <code>null</code> if there is a problem determining the element's
     * contents.
     * <p>
     * <strong>Note:</strong>: The client is responsible for closing the stream when finished.</p>
     *
     * @param element a structured element
     * @return the contents of the structured element, or <code>null</code>
     */
    InputStream getContents(Object element);
