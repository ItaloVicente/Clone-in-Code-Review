		ResourceLocator.imageDescriptorFromBundle(IDEWorkbenchPlugin.IDE_WORKBENCH,
				"$nl$/icons/full/obj16/fileType_filter.png").ifPresent(d -> fileIcon = d.createImage()); //$NON-NLS-1$
		ResourceLocator.imageDescriptorFromBundle(IDEWorkbenchPlugin.IDE_WORKBENCH,
				"$nl$/icons/full/obj16/folderType_filter.png").ifPresent(d -> folderIcon = d.createImage()); //$NON-NLS-1$
		ResourceLocator
				.imageDescriptorFromBundle(IDEWorkbenchPlugin.IDE_WORKBENCH,
						"$nl$/icons/full/obj16/fileFolderType_filter.png") //$NON-NLS-1$
				.ifPresent(d -> fileFolderIcon = d.createImage());
		ResourceLocator.imageDescriptorFromBundle(IDEWorkbenchPlugin.IDE_WORKBENCH,
				"$nl$/icons/full/obj16/includeMode_filter.png").ifPresent(d -> includeIcon = d.createImage()); //$NON-NLS-1$
		ResourceLocator.imageDescriptorFromBundle(IDEWorkbenchPlugin.IDE_WORKBENCH,
				"$nl$/icons/full/obj16/excludeMode_filter.png").ifPresent(d -> excludeIcon = d.createImage()); //$NON-NLS-1$
		ResourceLocator.imageDescriptorFromBundle(IDEWorkbenchPlugin.IDE_WORKBENCH,
				"$nl$/icons/full/obj16/inheritable_filter.png").ifPresent(d -> inheritableIcon = d.createImage()); //$NON-NLS-1$
