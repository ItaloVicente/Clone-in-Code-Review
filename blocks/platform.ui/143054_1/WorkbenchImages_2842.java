	}

	@Deprecated
	private static final void declareHoverImages() {
		declareImage(ISharedImages.IMG_TOOL_UNDO_HOVER, PATH_ETOOL + "undo_edit.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_REDO_HOVER, PATH_ETOOL + "redo_edit.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_CUT_HOVER, PATH_ETOOL + "cut_edit.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_COPY_HOVER, PATH_ETOOL + "copy_edit.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_PASTE_HOVER, PATH_ETOOL + "paste_edit.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_FORWARD_HOVER, PATH_ELOCALTOOL + "forward_nav.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_DELETE_HOVER, PATH_ETOOL + "delete_edit.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_NEW_WIZARD_HOVER, PATH_ETOOL + "new_wiz.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_BACK_HOVER, PATH_ELOCALTOOL + "backward_nav.png", true); //$NON-NLS-1$
		declareImage(ISharedImages.IMG_TOOL_UP_HOVER, PATH_ELOCALTOOL + "up_nav.png", true); //$NON-NLS-1$
	}

	public static void declareImage(String symbolicName, ImageDescriptor descriptor, boolean shared) {
		if (Policy.DEBUG_DECLARED_IMAGES) {
			Image image = descriptor.createImage(false);
			if (image == null) {
				WorkbenchPlugin.log("Image not found in WorkbenchImages.declaredImage().  symbolicName=" + symbolicName //$NON-NLS-1$
						+ " descriptor=" + descriptor, new Exception("stack dump")); //$NON-NLS-1$//$NON-NLS-2$
			} else {
				image.dispose();
			}
		}
		getDescriptors().put(symbolicName, descriptor);
		if (shared) {
			getImageRegistry().put(symbolicName, descriptor);
		}
	}

	public static Map<String, ImageDescriptor> getDescriptors() {
		if (descriptors == null) {
			initializeImageRegistry();
		}
		return descriptors;
	}

	public static Image getImage(String symbolicName) {
		return getImageRegistry().get(symbolicName);
	}

	public static ImageDescriptor getImageDescriptor(String symbolicName) {
		return getDescriptors().get(symbolicName);
	}


	public static ImageDescriptor getImageDescriptorFromProgram(String filename, int offset) {
		Assert.isNotNull(filename);
		String key = filename + "*" + offset; // use * as it is not a valid filename character//$NON-NLS-1$
		ImageDescriptor desc = getImageDescriptor(key);
		if (desc == null) {
			desc = new ProgramImageDescriptor(filename, offset);
			getDescriptors().put(key, desc);
		}
		return desc;
	}

	public static ImageRegistry getImageRegistry() {
		if (imageRegistry == null) {
			initializeImageRegistry();
		}
		return imageRegistry;
	}

