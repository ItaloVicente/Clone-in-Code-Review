		copyAction.setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
    }

	private ImageDescriptor createImageDescriptor(String relativeIconPath) {
		String ICONS_PATH = "$nl$/icons/full/";//$NON-NLS-1$
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		ImageDescriptor imageDescriptor = AbstractUIPlugin
				.imageDescriptorFromPlugin(String.valueOf(bundle.getBundleId()), ICONS_PATH + relativeIconPath);
		return imageDescriptor;
