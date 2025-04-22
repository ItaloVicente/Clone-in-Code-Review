
	@Override
	public void removed(IExtensionPoint[] extensionPoints) {
		WorkbenchPlugin.log("Removal of extension point not supported"); //$NON-NLS-1$
	}

	@Override
	public void removed(IExtension[] extensions) {
		WorkbenchPlugin.log("Removal of extensions not supported"); //$NON-NLS-1$
	}

	@Override
	public void added(IExtensionPoint[] extensionPoints) {
		WorkbenchPlugin.log("Removal of extension point not supported"); //$NON-NLS-1$
	}

	@Override
	public void added(IExtension[] extensions) {
		for (IExtension ext : extensions) {
			readExtension(ext);
		}
	}
