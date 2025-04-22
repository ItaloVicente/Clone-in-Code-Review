		return getPage().getWorkbenchWindow();
	}

	public WorkbenchPage getPage() {
		if (page == null) {
			page = (WorkbenchPage) ((WorkbenchWindow) PlatformUI
					.getWorkbench().getActiveWorkbenchWindow()).getActivePage();
		}
		return page;
	}
