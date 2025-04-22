		IStructuredSelection selection = (IStructuredSelection) visibleViewer
				.getSelection();
		Object[] selVCols = selection.toArray();
		List allVCols = getVisible();
		IColumnUpdater updater = doGetColumnUpdater();
		for (int i = selVCols.length - 1; i >= 0; i--) {
			Object colObj = selVCols[i];
