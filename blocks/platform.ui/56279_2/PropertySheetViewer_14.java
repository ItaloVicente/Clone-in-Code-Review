		TreeItem[] sel = tree.getSelection();
		List entries = new ArrayList(sel.length);
		for (int i = 0; i < sel.length; i++) {
			TreeItem ti = sel[i];
			Object data = ti.getData();
			if (data instanceof IPropertySheetEntry) {
