     * Notifies any selection changed listeners that the selected page
     * has changed.
     * Only listeners registered at the time this method is called are notified.
     *
     * @param event a selection changed event
     *
     * @see IPageChangedListener#pageChanged
     *
     * @since 3.1
     */
    protected void firePageChanged(final PageChangedEvent event) {
