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

	private MyModel[] createModel() {
		MyModel[] elements = new MyModel[10];

		for (int i = 0; i < 10; i++) {
			elements[i] = new MyModel(i);
		}

		return elements;
