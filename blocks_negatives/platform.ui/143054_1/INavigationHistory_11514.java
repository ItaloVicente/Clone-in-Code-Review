    /**
     * Mark the current location into the history. This message
     * should be sent by clients whenever significant changes
     * in location are detected.
     *
     * The location is obtained by calling <code>INavigationLocationProvider.createNavigationLocation</code>
     *
     * @param part the editor part
     */
    public void markLocation(IEditorPart part);
