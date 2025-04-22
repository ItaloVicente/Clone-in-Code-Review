			IWorkbenchWindow activeWorkbenchWindow = HandlerUtil
					.getActiveWorkbenchWindow(event);
			if (activeWorkbenchWindow != null) {
				IWorkbenchPage page = activeWorkbenchWindow.getActivePage();
				if (page != null) {
					view = (IHistoryView) page.showView(IHistoryView.VIEW_ID);
					IResource[] resources = getSelectedResources(event);
					if (resources.length == 1) {
						view.showHistoryFor(resources[0]);
						return null;
					}
					HistoryPageInput list = new HistoryPageInput(repo,
							resources);
					view.showHistoryFor(list);
				}
