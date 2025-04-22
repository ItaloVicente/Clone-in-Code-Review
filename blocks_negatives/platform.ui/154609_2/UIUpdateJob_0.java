			if (view.isVisible()) {
				/*
				 * we prefer not to check for cancellation beyond this since we have to show correct
				 * marker counts on UI, not an updating message.
				 */
				IContentProvider contentProvider= viewer.getContentProvider();
				contentProvider.inputChanged(viewer, view.getViewerInput(), clone);

				viewer.getTree().setRedraw(false);
				viewer.refresh(true);
				if (!monitor.isCanceled()) {
					view.reexpandCategories();
				}
				view.getBuilder().resetChangeFlags();
