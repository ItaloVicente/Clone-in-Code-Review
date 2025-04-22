		if (window == null)
			window = getActiveWorkbenchWindow();

		IWorkbenchPage page = window == null ? null : window.getActivePage();
		if (page == null)
			return null;

		IPerspectiveRegistry registry = getPerspectiveRegistry();
		IPerspectiveDescriptor perspective = registry.findPerspectiveWithId(perspectiveId);
		page.setPerspective(registry.findPerspectiveWithId(perspectiveId));
		page.setPerspective(perspective);
		return page;
