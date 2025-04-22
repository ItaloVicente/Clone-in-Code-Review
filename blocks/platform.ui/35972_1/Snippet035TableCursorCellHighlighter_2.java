		TableViewerColumn viewerColumn = new TableViewerColumn(v, SWT.NONE);
		viewerColumn.getColumn().setWidth(200);
		viewerColumn.getColumn().setText("Column 1");
		viewerColumn.setEditingSupport(new MyEditingSupport(v, "1"));
		viewerColumn.setLabelProvider(new MyColumnLabelProvider("1"));

		viewerColumn = new TableViewerColumn(v, SWT.NONE);
		viewerColumn.getColumn().setWidth(200);
		viewerColumn.getColumn().setText("Column 2");
		viewerColumn.setEditingSupport(new MyEditingSupport(v, "2"));
		viewerColumn.setLabelProvider(new MyColumnLabelProvider("2"));
