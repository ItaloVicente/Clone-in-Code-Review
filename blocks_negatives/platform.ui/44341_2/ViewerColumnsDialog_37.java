		IStructuredSelection selection = (IStructuredSelection) visibleViewer
				.getSelection();
		Object[] selVCols = selection.toArray();
		List allVCols = getVisible();
		IColumnUpdater updater = doGetColumnUpdater();
		for (int i = 0; i < selVCols.length; i++) {
			Object colObj = selVCols[i];
