				handleVariablesButtonPressed();
			}
		});
		variablesButton.setEnabled(enabled);
	}

	public String getLinkTarget() {
		if (createLink && linkTargetField != null && linkTargetField.isDisposed() == false) {
			return linkTargetField.getText();
		}
		return null;
	}

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
