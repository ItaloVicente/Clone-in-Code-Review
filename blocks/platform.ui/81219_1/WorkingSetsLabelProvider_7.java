	private Image getWorkingSetImage() {
		if (workingSetImage == null) {
			URL iconUrl = FileLocator.find(WorkbenchNavigatorPlugin.getDefault().getBundle(),
					Path.fromPortableString("icons/full/obj16/workingsets.png"), //$NON-NLS-1$
					Collections.emptyMap());
			workingSetImage = ImageDescriptor.createFromURL(iconUrl).createImage();
		}
		return workingSetImage;
	}

