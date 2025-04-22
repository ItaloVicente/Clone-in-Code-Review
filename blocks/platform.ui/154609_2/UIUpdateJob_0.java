			IContentProvider contentProvider = viewer.getContentProvider();
			contentProvider.inputChanged(viewer, view.getViewerInput(), clone);

			viewer.getTree().setRedraw(false);
			viewer.refresh(true);
			if (!monitor.isCanceled()) {
				view.reexpandCategories();
