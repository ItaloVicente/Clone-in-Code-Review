		showLinkedResources = getShowLinkedResources();
		this.resourceGroup = new ResourceTreeAndListGroup(parent, input,
				getResourceProvider(IResource.FOLDER | IResource.PROJECT, showLinkedResources),
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(),
				getResourceProvider(IResource.FILE, showLinkedResources),
				WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider(), SWT.NONE,
				DialogUtil.inRegularFontMode(parent));
