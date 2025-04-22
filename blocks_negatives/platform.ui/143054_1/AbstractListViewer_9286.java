            }
        } else {
            doUpdateItem(list, element, true);
        }
    }

    /**
     * Returns the index of the item currently at the top of the viewable area.
     * <p>
     * Default implementation returns -1.
     * </p>
     * @return index, -1 for none
     * @since 3.3
     */
    protected int listGetTopIndex(){
    	return -1;
    }

    /**
     * Sets the index of the item to be at the top of the viewable area.
     * <p>
     * Default implementation does nothing.
     * </p>
     * @param index the given index. -1 for none.  index will always refer to a valid index.
     * @since 3.3
     */
    protected void listSetTopIndex(int index) {
    }

    /**
     * Removes the given elements from this list viewer.
     *
     * @param elements the elements to remove
     */
    private void internalRemove(final Object[] elements) {
        Object input = getInput();
        for (Object element : elements) {
            if (equals(element, input)) {
                setInput(null);
                return;
            }
            int ix = getElementIndex(element);
            if (ix >= 0) {
                listRemove(ix);
                listMap.remove(ix);
                unmapElement(element, getControl());
            }
        }
    }

    /**
     * Removes the given elements from this list viewer.
     * The selection is updated if required.
     * <p>
     * This method should be called (by the content provider) when elements
     * have been removed from the model, in order to cause the viewer to accurately
     * reflect the model. This method only affects the viewer, not the model.
     * </p>
     *
     * @param elements the elements to remove
     */
    public void remove(final Object[] elements) {
        assertElementsNotNull(elements);
        if (elements.length == 0) {
        	return;
        }
        preservingSelection(() -> internalRemove(elements));
    }

    /**
     * Removes the given element from this list viewer.
     * The selection is updated if necessary.
     * <p>
     * This method should be called (by the content provider) when a single element
     * has been removed from the model, in order to cause the viewer to accurately
     * reflect the model. This method only affects the viewer, not the model.
     * Note that there is another method for efficiently processing the simultaneous
     * removal of multiple elements.
     * </p>
     *
     * @param element the element
     */
    public void remove(Object element) {
        remove(new Object[] { element });
    }

    /**
