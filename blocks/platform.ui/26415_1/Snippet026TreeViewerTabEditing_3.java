		for (int i = 0; i < columLabels.length; i++) {
			TreeViewerColumn column = new TreeViewerColumn(v, SWT.NONE);
			column.getColumn().setWidth(200);
			column.getColumn().setMoveable(true);
			column.getColumn().setText(columLabels[i]);
			column.setLabelProvider(createColumnLabelProvider(labelPrefix[i]));
			column.setEditingSupport(createEditingSupportFor(v, textCellEditor));
		}
		v.setContentProvider(new MyContentProvider());
		v.setInput(createModel());
	}
