    /**
     * Notifies that the end user has canceled editing.
     * All cell editors send this notification.
     * A listener should <b>not</b> update the model based on this
     * notification; see <code>applyEditorValue</code>.
     */
    public void cancelEditor();
