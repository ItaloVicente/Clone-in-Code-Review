		return canvas;
	}

	private void initializeListeners() {
		canvas.addPaintListener(event -> {

			GC gc = event.gc;
			Transform transform = null;
			if (orientation == SWT.VERTICAL) {
				transform = new Transform(event.display);
				transform.translate(TrimUtil.TRIM_DEFAULT_HEIGHT, 0);
				transform.rotate(90);
			}
			ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();

			int itemCount = Math.min(displayedItems.length, numShowItems);

			int yOffset = 0;
			int xOffset = 0;
			if (numShowItems == 1) {// If there is a single item try to center it
				Rectangle clientArea = canvas.getParent().getClientArea();
				if (orientation == SWT.HORIZONTAL) {
					int size1 = clientArea.height;
					yOffset = size1 - (fontMetrics.getHeight());
					yOffset = yOffset / 2;
				} else {
					int size2 = clientArea.width;
					xOffset = size2 - (fontMetrics.getHeight());
					xOffset = xOffset / 2;
				}
			}

			for (int i = 0; i < itemCount; i++) {
				String string = labelProvider.getText(displayedItems[i]);
				if (string == null) {
