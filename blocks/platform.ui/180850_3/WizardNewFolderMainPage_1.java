	@Override
	public void dispose() {
		disposeAdvancedControls();
		super.dispose();
	}

	private void disposeAdvancedControls() {
		if (linkedResourceComposite != null) {
			linkedResourceComposite.dispose();
			linkedResourceComposite = null;
			filterButton.dispose();
			useDefaultLocation.dispose();
			useVirtualFolder.dispose();
			useLinkedResource.dispose();
			linkedGroupComposite.dispose();
			folderImage.dispose();
			virtualFolderImage.dispose();
			linkedFolderImage.dispose();
			filterButton = null;
			useDefaultLocation = null;
			useVirtualFolder = null;
			useLinkedResource = null;
			linkedGroupComposite = null;
		}
	}

