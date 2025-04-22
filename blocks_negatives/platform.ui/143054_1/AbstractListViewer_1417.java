    /**
     * A list of viewer elements (element type: <code>Object</code>).
     */
    private java.util.List listMap = new ArrayList();

    /**
     * Adds the given string to the underlying widget at the given index
     *
     * @param string the string to add
     * @param index position to insert the string into
     */
    protected abstract void listAdd(String string, int index);

    /**
     * Sets the text of the item at the given index in the underlying widget.
     *
     * @param index index to modify
     * @param string new text
     */
    protected abstract void listSetItem(int index, String string);

    /**
     * Returns the zero-relative indices of the items which are currently
     * selected in the underlying widget.  The array is empty if no items are selected.
     * <p>
     * Note: This is not the actual structure used by the receiver
     * to maintain its selection, so modifying the array will
     * not affect the receiver.
     * </p>
     * @return the array of indices of the selected items
     */
    protected abstract int[] listGetSelectionIndices();

    /**
     * Returns the number of items contained in the underlying widget.
     *
     * @return the number of items
     */
    protected abstract int listGetItemCount();

    /**
     * Sets the underlying widget's items to be the given array of items.
     *
     * @param labels the array of label text
     */
    protected abstract void listSetItems(String[] labels);

    /**
     * Removes all of the items from the underlying widget.
     */
    protected abstract void listRemoveAll();

    /**
     * Removes the item from the underlying widget at the given
     * zero-relative index.
     *
     * @param index the index for the item
     */
    protected abstract void listRemove(int index);

    /**
     * Selects the items at the given zero-relative indices in the underlying widget.
     * The current selection is cleared before the new items are selected.
     * <p>
     * Indices that are out of range and duplicate indices are ignored.
     * If the receiver is single-select and multiple indices are specified,
     * then all indices are ignored.
     *
     * @param ixs the indices of the items to select
     */
    protected abstract void listSetSelection(int[] ixs);

    /**
     * Shows the selection.  If the selection is already showing in the receiver,
     * this method simply returns.  Otherwise, the items are scrolled until
     * the selection is visible.
     */
    protected abstract void listShowSelection();

    /**
     * Deselects all selected items in the underlying widget.
     */
    protected abstract void listDeselectAll();

    /**
     * Adds the given elements to this list viewer.
     * If this viewer does not have a sorter, the elements are added at the end
     * in the order given; otherwise the elements are inserted at appropriate positions.
     * <p>
     * This method should be called (by the content provider) when elements
     * have been added to the model, in order to cause the viewer to accurately
     * reflect the model. This method only affects the viewer, not the model.
     * </p>
     *
     * @param elements the elements to add
     */
    public void add(Object[] elements) {
        assertElementsNotNull(elements);
        Object[] filtered = filter(elements);
        ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();
        for (Object element : filtered) {
            int ix = indexForElement(element);
            insertItem(labelProvider, element, ix);
        }
    }

    private void insertItem(ILabelProvider labelProvider, Object element, int index) {
        listAdd(getLabelProviderText(labelProvider, element), index);
