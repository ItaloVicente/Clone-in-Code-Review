		IStructuredSelection selection = (IStructuredSelection) visibleViewer.getSelection();
		@SuppressWarnings("unchecked")
		List<T> selVCols = selection.toList();
		List<T> allVCols = getVisible();
		IColumnUpdater<T> updater = doGetColumnUpdater();
		for (int i = 0; i < selVCols.size(); i++) {
			T colObj = selVCols.get(i);
