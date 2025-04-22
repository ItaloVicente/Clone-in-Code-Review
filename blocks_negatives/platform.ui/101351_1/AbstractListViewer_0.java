    /**
     * Adds the given element to this list viewer.
     * If this viewer does not have a sorter, the element is added at the end;
     * otherwise the element is inserted at the appropriate position.
     * <p>
     * This method should be called (by the content provider) when a single element
     * has been added to the model, in order to cause the viewer to accurately
     * reflect the model. This method only affects the viewer, not the model.
     * Note that there is another method for efficiently processing the simultaneous
     * addition of multiple elements.
     * </p>
     *
     * @param element the element
     */
    public void add(Object element) {
        add(new Object[] { element });
    }

