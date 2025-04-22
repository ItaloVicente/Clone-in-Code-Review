	}

	private void initGrid(Control[] children) {
		cache.setControls(children);
		gridInfo.initGrid(children, this);
		cachedRowMin = null;
		cachedColMin = null;
	}

	@Override
	protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache) {
		Control[] children = composite.getChildren();
		initGrid(children);

		if (flushCache) {
			cache.flush();
		}

		Point emptySpace = totalEmptySpace();

		int[] heightConstraints = computeConstraints(true);

		int width;
		if (wHint == SWT.DEFAULT) {
			width = preferredSize(heightConstraints, false);
		} else {
			width = wHint - emptySpace.x;
		}

		int height = hHint;
		if (hHint == SWT.DEFAULT) {
			height = preferredSize(computeSizes(heightConstraints, width, false), true);
		} else {
			height = hHint - emptySpace.y;
		}

		Point preferredSize = new Point(width + emptySpace.x, height + emptySpace.y);


		Point minimumSize = CellLayoutUtil.computeMinimumSize(composite);

		boolean wider = (preferredSize.x >= minimumSize.x);
		boolean taller = (preferredSize.y >= minimumSize.y);

		if (wider) {
			if (taller) {
				return preferredSize;
			}
