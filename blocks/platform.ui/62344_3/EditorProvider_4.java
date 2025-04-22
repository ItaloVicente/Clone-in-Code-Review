			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			if (window == null) {
				return new QuickAccessElement[0];
			}
			IWorkbenchPage activePage = window.getActivePage();
