                handleVariablesButtonPressed();
            }
        });
        variablesButton.setEnabled(enabled);
    }

    /**
     * Returns the link target location entered by the user.
     *
     * @return the link target location entered by the user. null if the user
     * 	choose not to create a link.
     */
    public String getLinkTarget() {
        if (createLink && linkTargetField != null
                && linkTargetField.isDisposed() == false) {
            return linkTargetField.getText();
        }
        return null;
    }

    /**
     * Opens a file or directory browser depending on the link type.
     */
    private void handleLinkTargetBrowseButtonPressed() {
        String linkTargetName = linkTargetField.getText();
         String selection = null;
         IFileStore store = null;
        if (linkTargetName.length() > 0) {
            store = IDEResourceInfoUtils.getFileStore(linkTargetName);
            if (store == null || !store.fetchInfo().exists()) {
            	store = null;
            }
        }
        if (type == IResource.FILE) {
            FileDialog dialog = new FileDialog(getShell(), SWT.SHEET);
            if (store != null) {
                if (store.fetchInfo().isDirectory()) {
