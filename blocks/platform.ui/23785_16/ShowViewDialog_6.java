
		treeViewer.setLabelProvider(new ViewLabelProvider(context, modelService, window,
				dimmedForeground));
		treeViewer.setContentProvider(new ViewContentProvider(application));
		treeViewer.setComparator(new ViewComparator());
		treeViewer.setInput(application);
