    /**
     * Returns the image for the label of the given element.  The image
     * is owned by the label provider and must not be disposed directly.
     * Instead, dispose the label provider when no longer needed.
     *
     * @param element the element for which to provide the label image
     * @return the image used to label the element, or <code>null</code>
     *   if there is no image for the given object
     */
    public Image getImage(Object element);
