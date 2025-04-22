    /**
     * Notifies that the end user is changing the value in the cell editor. This
     * notification is normally sent only by text-based editors in response to a
     * keystroke, so that the listener may show an error message reflecting the
     * current valid state. This notification is sent while the value is being
     * actively edited, before the value is applied or canceled.  A listener should
     * <b>not</b> update the model based on this notification; see
     * <code>applyEditorValue</code>.
     * <p>
     * If the <code>newValidState</code> parameter is <code>true</code>,
     * the new value may be retrieved by calling <code>ICellEditor.getValue</code>
     * on the appropriate cell editor.
     * </p>
     *
     * @param oldValidState the valid state before the end user changed the value
     * @param newValidState the current valid state
     */
    public void editorValueChanged(boolean oldValidState, boolean newValidState);
