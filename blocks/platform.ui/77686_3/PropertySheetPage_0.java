		if (viewer != null) {
			if (selectionChangeListener != null) {
				viewer.removeSelectionChangedListener(selectionChangeListener);
				selectionChangeListener = null;
			}
			viewer.dispose();
			cellEditorActivationListener = null;
			viewer = null;
		}
		if (defaultsAction != null) {
			defaultsAction.viewer = null;
			defaultsAction = null;
		}
		if (filterAction != null) {
			filterAction.viewer = null;
			filterAction = null;
		}
		if (categoriesAction != null) {
			categoriesAction.viewer = null;
			categoriesAction = null;
		}
		if (copyAction != null) {
			copyAction.viewer = null;
			copyAction = null;
		}
		sorter = null;
		provider = null;
