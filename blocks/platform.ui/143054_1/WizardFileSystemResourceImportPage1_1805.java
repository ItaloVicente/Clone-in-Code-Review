	public boolean finish() {
		if (!ensureSourceIsValid()) {
			return false;
		}

		saveWidgetValues();

		Iterator resourcesEnum = getSelectedResources().iterator();
		List fileSystemObjects = new ArrayList();
		while (resourcesEnum.hasNext()) {
			fileSystemObjects.add(((FileSystemElement) resourcesEnum.next())
					.getFileSystemObject());
		}
