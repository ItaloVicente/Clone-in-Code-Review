    }

    /**
     * Returns the indices referring the current selection.
     * To be called within open().
     * @return returns the indices of the current selection.
     */
    protected int[] getSelectionIndices() {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getSelectionIndices();
    }

    /**
     * Returns an index referring the first current selection.
     * To be called within open().
     * @return returns the indices of the current selection.
     */
    protected int getSelectionIndex() {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getSelectionIndex();
    }

    /**
     * Sets the selection referenced by an array of elements.
     * Empty or null array removes selection.
     * To be called within open().
     * @param selection the indices of the selection.
     */
    protected void setSelection(Object[] selection) {
        Assert.isNotNull(fFilteredList);
        fFilteredList.setSelection(selection);
    }

    /**
     * Returns an array of the currently selected elements.
     * To be called within or after open().
     * @return returns an array of the currently selected elements.
     */
    protected Object[] getSelectedElements() {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getSelection();
    }

    /**
     * Returns all elements which are folded together to one entry in the list.
     * @param  index the index selecting the entry in the list.
     * @return returns an array of elements folded together.
     */
    public Object[] getFoldedElements(int index) {
        Assert.isNotNull(fFilteredList);
        return fFilteredList.getFoldedElements(index);
    }

    /**
     * Creates the message text widget and sets layout data.
     * @param composite the parent composite of the message area.
     */
    @Override
