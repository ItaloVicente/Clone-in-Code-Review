		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			IWorkbenchPart part = window.getActivePage().findView(ProjectExplorer.VIEW_ID);
			if (part != null) {
				contentService = part.getAdapter(INavigatorContentService.class);
				if (contentService != null) {
					labelProvider = contentService.createCommonLabelProvider();
					descriptionProvider = contentService.createCommonDescriptionProvider();
				} else {
					WorkbenchNavigatorPlugin.log(
							"Could not acquire INavigatorContentService from part (\"" + part.getTitle() + "\").", //$NON-NLS-1$ //$NON-NLS-2$
							null);
				}
			} else {
				WorkbenchNavigatorPlugin.log("Could not acquire INavigatorContentService: Project Explorer not found.", //$NON-NLS-1$
						null);
			}
