	/**
	 * Actually sets the paint listener.
	 *
	 * @param color
	 *            the foreground color to be used (if null removes the paint
	 *            listener).
	 */
	private void setPaintListener(Color color) {
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			Tree tree = getTree();

			tree.removeListener(SWT.PaintItem, treeItemSquaresPaintListener);
			tree.removeListener(SWT.PaintItem, treeItemArrowsPaintListener);

			if (color != null) {
				String treeArrowsMode = getTreeArrowsMode();
				if (TREE_ARROWS_MODE_TRIANGLE.equals(treeArrowsMode)) {
					tree.addListener(SWT.PaintItem, treeItemArrowsPaintListener);
				} else if (TREE_ARROWS_MODE_SQUARE.equals(treeArrowsMode)) {
					tree.addListener(SWT.PaintItem, treeItemSquaresPaintListener);
				} else if (!showedUnsupportedWarning) {
					System.err.println("Unsupported swt-tree-arrow-mode: " + treeArrowsMode);
					showedUnsupportedWarning = true;
				}
			}
		} else if (!showedUnsupportedWarning) {
			System.err.println("swt-tree-arrow-mode and swt-tree-arrow-color are not supported on this platform");
			showedUnsupportedWarning = true;
		}
	}

	/**
	 * Adds a custom paint listener which replaces the original tree arrows and
	 * draws new ones (based on the state of the TreeItem) if a color is passed
	 * (if null is passed, returns to the standard behavior).
	 *
	 * @param color
	 *            The foreground color to be used to paint the items. May be
	 *            null (in which case we stop our custom painter from painting
	 *            the tree items).
	 */
	public void setTreeArrowsForegroundColor(Color color) {
		Tree tree = getTree();
		tree.setData(TREE_ARROWS_FOREGROUND_COLOR, color);
		setPaintListener(color);
	}

	/**
	 * @return the color to be used to draw the tree arrows foreground.
	 */
	public Color getTreeArrowsForegroundColor() {
		Tree tree = getTree();
		Object data = tree.getData(TREE_ARROWS_FOREGROUND_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}

	/**
	 * Sets the way to draw the tree arrows.
	 *
	 * @see #TREE_ARROWS_MODE_TRIANGLE
	 * @see #TREE_ARROWS_MODE_SQUARE
	 */
	public void setTreeArrowsMode(String arrowsMode) {
		Tree tree = getTree();
		if (arrowsMode == null) {
			tree.setData(TREE_ARROWS_MODE, null);
			setPaintListener(getTreeArrowsForegroundColor());
			return;
		}
		Assert.isTrue(TREE_ARROWS_MODE_TRIANGLE.equals(arrowsMode) || TREE_ARROWS_MODE_SQUARE.equals(arrowsMode));
		tree.setData(TREE_ARROWS_MODE, arrowsMode);
	}

	/**
	 * @return the way to draw the tree arrows.
	 *
	 * @see #TREE_ARROWS_MODE_TRIANGLE
	 * @see #TREE_ARROWS_MODE_SQUARE
	 */
	public String getTreeArrowsMode() {
		Tree tree = getTree();
		Object data = tree.getData(TREE_ARROWS_MODE);
		if (TREE_ARROWS_MODE_TRIANGLE.equals(data) || TREE_ARROWS_MODE_SQUARE.equals(data)) {
			return (String) data;
		}
		return TREE_ARROWS_MODE_TRIANGLE;
	}

	@Override
	public void setSelectionBackgroundColor(Color color) {
		this.fControlSelectedColorCustomization.setSelectionBackgroundColor(color);
	}

	@Override
	public Color getSelectionBackgroundColor() {
		return this.fControlSelectedColorCustomization.getSelectionBackgroundColor();
	}

	@Override
	public void setSelectionBorderColor(Color color) {
		this.fControlSelectedColorCustomization.setSelectionBorderColor(color);

	}

	@Override
	public Color getSelectionBorderColor() {
		return this.fControlSelectedColorCustomization.getSelectionBorderColor();
	}

	@Override
	public void setHotBackgroundColor(Color color) {
		this.fControlSelectedColorCustomization.setHotBackgroundColor(color);

	}

	@Override
	public Color getHotBackgroundColor() {
		return this.fControlSelectedColorCustomization.getHotBackgroundColor();
	}

	@Override
	public void setHotBorderColor(Color color) {
		this.fControlSelectedColorCustomization.setHotBorderColor(color);
	}

	@Override
	public Color getHotBorderColor() {
		return this.fControlSelectedColorCustomization.getHotBorderColor();
	}

	@Override
	public Color getSelectionForegroundColor() {
		return this.fControlSelectedColorCustomization.getSelectionForegroundColor();
	}

	@Override
	public void setSelectionForegroundColor(Color color) {
		this.fControlSelectedColorCustomization.setSelectionForegroundColor(color);
	}

