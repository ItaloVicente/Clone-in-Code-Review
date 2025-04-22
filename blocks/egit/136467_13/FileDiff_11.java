	public ImageDescriptor getBaseImageDescriptor() {
		if (!isSubmodule()) {
			return UIUtils.getEditorImage(getPath());
		} else {
			return UIIcons.REPOSITORY;
		}
	}

	public ImageDescriptor getImageDcoration() {
