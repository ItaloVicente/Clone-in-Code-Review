		}
		fViewer.setContentProvider(fContentProvider);
		fViewer.setLabelProvider(fLabelProvider);
		fViewer.addCheckStateListener(event -> updateOKStatus());
		fViewer.setComparator(fComparator);
		if (fFilters != null) {
			for (int i = 0; i != fFilters.size(); i++) {
