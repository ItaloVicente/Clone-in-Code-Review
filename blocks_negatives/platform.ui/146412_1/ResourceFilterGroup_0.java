		ImageDescriptor fileIconDescriptor = AbstractUIPlugin
		.imageDescriptorFromPlugin(IDEWorkbenchPlugin.IDE_WORKBENCH,
		if (fileIconDescriptor != null)
			fileIcon = fileIconDescriptor.createImage();

		ImageDescriptor folderIconDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				IDEWorkbenchPlugin.IDE_WORKBENCH,
		if (folderIconDescriptor != null)
			folderIcon = folderIconDescriptor.createImage();

		ImageDescriptor fileFolderIconDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				IDEWorkbenchPlugin.IDE_WORKBENCH,
		if (fileFolderIconDescriptor != null)
			fileFolderIcon = fileFolderIconDescriptor.createImage();

		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				IDEWorkbenchPlugin.IDE_WORKBENCH,
		if (descriptor != null)
			includeIcon = descriptor.createImage();

		descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				IDEWorkbenchPlugin.IDE_WORKBENCH,
		if (descriptor != null)
			excludeIcon = descriptor.createImage();

		ImageDescriptor inheritableIconDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
				IDEWorkbenchPlugin.IDE_WORKBENCH,
		if (inheritableIconDescriptor != null)
			inheritableIcon = inheritableIconDescriptor.createImage();
