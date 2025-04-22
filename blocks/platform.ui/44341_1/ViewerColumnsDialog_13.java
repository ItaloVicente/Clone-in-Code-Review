		IStructuredSelection selection = (IStructuredSelection) visibleViewer.getSelection();
		@SuppressWarnings("unchecked")
		List<T> selVCols = selection.toList();
		List<T> allVCols = getVisible();
		IColumnUpdater<T> updater = doGetColumnUpdater();
		for (int i = selVCols.size() - 1; i >= 0; i--) {
			T colObj = selVCols.get(i);
