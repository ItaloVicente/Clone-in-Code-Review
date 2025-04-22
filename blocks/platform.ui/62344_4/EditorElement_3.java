			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			if (window != null) {
				IWorkbenchPage activePage = window.getActivePage();
				if (activePage != null) {
					activePage.activate(part);
				}
