
						for (IWorkbenchWindow window : getWorkbenchWindows()) {
							IWorkbenchPage page = window.getActivePage();
							if (page != null) {
								((WorkbenchPage) page).fireInitialPartVisibilityEvents();
							}
						}
