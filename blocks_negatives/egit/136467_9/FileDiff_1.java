	@Override
	public ImageDescriptor getImageDescriptor(Object object) {
		final ImageDescriptor base;
		if (!isSubmodule())
			base = UIUtils.getEditorImage(getPath());
		else
			base = UIIcons.REPOSITORY;
