		v.setLabelProvider(new MyLabelProvider());
		v.setContentProvider(new MyContentProvider());

		TableColumn column = new TableColumn(v.getTable(), SWT.NONE);
		column.setWidth(200);
		column.setText("Column 1");

		column = new TableColumn(v.getTable(), SWT.NONE);
		column.setWidth(200);
		column.setText("Column 2");

		MyModel[] model = createModel();
		v.setInput(model);
		v.getTable().setLinesVisible(true);
		v.getTable().setHeaderVisible(true);

		DefaultToolTip toolTip = new DefaultToolTip(v.getControl(),
				ToolTip.NO_RECREATE, false);
		toolTip.setText("Hello World\nHello World");
		toolTip.setBackgroundColor(v.getTable().getDisplay().getSystemColor(
				SWT.COLOR_RED));
		toolTip.setShift(new Point(10, 5));
	}
