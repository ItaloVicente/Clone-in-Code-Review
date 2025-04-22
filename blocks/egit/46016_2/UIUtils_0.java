		if (path == null || path.length() <= 0) {
			return DEFAULT_FILE_IMG;
		}
		final String fileName = new Path(path).lastSegment();
		if (fileName == null) {
			return DEFAULT_FILE_IMG;
		}
		IEditorRegistry registry = PlatformUI.getWorkbench()
				.getEditorRegistry();
		IEditorDescriptor defaultEditor = registry.getDefaultEditor(fileName);
		if (defaultEditor != null) {
			return defaultEditor.getImageDescriptor();
		}
		int extensionIndex = fileName.lastIndexOf('.');
		if (extensionIndex < 0) {
			return DEFAULT_FILE_IMG;
		}
		String key = fileName.substring(extensionIndex);
		SoftReference<ImageDescriptor> cached = extensionToDescriptor.get(key);
		if (cached != null) {
			ImageDescriptor descriptor = cached.get();
			if (descriptor != null) {
				return descriptor;
			}
