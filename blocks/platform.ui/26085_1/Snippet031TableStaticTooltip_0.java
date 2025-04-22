	private static Image createImage(Display display, int red, int green,
			int blue) {
		Color color = new Color(display, red, green, blue);
		Image image = new Image(display, 10, 10);
		GC gc = new GC(image);
		gc.setBackground(color);
		gc.fillRectangle(0, 0, 10, 10);
		gc.dispose();

		return image;
	}

	private void createToolTipFor(final TableViewer viewer) {
		DefaultToolTip toolTip = new DefaultToolTip(viewer.getControl(),
				ToolTip.NO_RECREATE, false);

		toolTip.setText("Hello World\nHello World");
		toolTip.setBackgroundColor(viewer.getTable().getDisplay()
				.getSystemColor(SWT.COLOR_RED));

		toolTip.setShift(new Point(10, 5));
	}

	private TableColumn createColumnFor(TableViewer viewer, String label) {
		TableColumn column = new TableColumn(viewer.getTable(), SWT.NONE);
		column.setWidth(200);
		column.setText(label);
		return column;
	}

	private MyModel[] createModel() {
		MyModel[] elements = new MyModel[10];

		for (int i = 0; i < elements.length; i++) {
			elements[i] = new MyModel(i);
		}
		return elements;
	}

