    /**
     * Adds a synchronized pagebook to this editor.  Once added, the
     * visible page of the pagebook and the visible page of the editor
     * will be synchronized.
     *
     * @param pageBook the pagebook to add
     */
    protected void addSyncroPageBook(PageBook pageBook) {
        if (syncVector == null) {
