		IWorkbenchWindow workbenchWindow = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		WorkbenchPage page = (WorkbenchPage) workbenchWindow.getActivePage();
		if (page != null) {
			MUIElement area = page.findSharedArea();
			if (area instanceof MPlaceholder) {
				area = ((MPlaceholder) area).getRef();
			}
