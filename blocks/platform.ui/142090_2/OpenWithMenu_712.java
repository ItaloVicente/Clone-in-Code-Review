	}

	private Image getImage(IEditorDescriptor editorDesc) {
		ImageDescriptor imageDesc = getImageDescriptor(editorDesc);
		if (imageDesc == null) {
			return null;
		}
		return IDEWorkbenchPlugin.getDefault().getResourceManager().createImage(imageDesc);
	}

	private ImageDescriptor getImageDescriptor(IEditorDescriptor editorDesc) {
		ImageDescriptor imageDesc = null;
		if (editorDesc == null) {
