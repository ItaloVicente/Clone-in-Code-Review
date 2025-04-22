    public TextMessageDialogs(String name) {
        super(name);
    }

    private Shell getShell() {
        return DialogCheck.getShell();
    }

    /**
     * Returns the given string from the Text Editor's resource bundle.
     * Should call org.eclipse.ui.texteditor.EditorMessages directly,
     * but it has package visibility.
     */
    private String getEditorString(String id) {
        ResourceBundle bundle = ResourceBundle
                .getBundle("org.eclipse.ui.texteditor.EditorMessages");
        assertNotNull("EditorMessages", bundle);
        String string = bundle.getString(id);
        assertNotNull(id, string);
        return string;
    }

    /*
     * Convenience method simliar to org.eclipse.jface.dialogs.MessageDialog::openConfirm.
     * The method will return the dialog instead of opening.
     * @param title the dialog's title, or <code>null</code> if none.
     * @param message the message.
     * @return Dialog the confirm dialog.
     */
    private MessageDialog getConfirmDialog(String title, String message) {
        return new MessageDialog(getShell(), title, null, message,
