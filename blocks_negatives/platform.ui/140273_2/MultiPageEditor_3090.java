    /**
     * Sets the visible page of the given pagebook to be the same as
     * the visible page of this editor.
     *
     * @param pageBook a pagebook to synchronize
     */
    protected void syncPageBook(PageBook pageBook) {
        int pos = tabFolder.getSelectionIndex();
        Control children[] = pageBook.getChildren();
        int size = children.length;
        if (pos < size) {
