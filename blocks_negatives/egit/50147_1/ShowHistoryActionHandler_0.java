			view = (IHistoryView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().showView(
							IHistoryView.VIEW_ID);
			IResource[] resources = getSelectedResources(event);
			if (resources.length == 1) {
				view.showHistoryFor(resources[0]);
				return null;
