		Image image = DEFAULT;
		String name = new Path(diff.getPath()).lastSegment();
		if (name != null) {
			ImageDescriptor descriptor = PlatformUI.getWorkbench()
					.getEditorRegistry().getImageDescriptor(name);
			image = (Image) this.resourceManager.get(descriptor);
