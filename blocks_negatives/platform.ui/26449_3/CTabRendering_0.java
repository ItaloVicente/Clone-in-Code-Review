
		int selectedItemWidth = parent.getBounds().width;
		if (parent.getSelectionIndex() > -1) {
			Control control = parent.getSelection().getControl();
			if (control != null)
				selectedItemWidth = control.getBounds().width;
		}

		int leftRightBorder = (parent.getBounds().width - selectedItemWidth) / 2;
		int topBorder = INNER_KEYLINE + OUTER_KEYLINE;
		Control toolbarContainer = findToolbarContainer();

		if (toolbarContainer != null) {
			if (toolbarContainer.getBounds().height > 0) {
				int unselectedHeightToDraw = Math.max(
						toolbarContainer.getBounds().height,
						partHeaderBounds.height);

				rendererWrapper.drawBackground(gc, partHeaderBounds.x
						+ leftRightBorder, partHeaderBounds.height + topBorder,
						partHeaderBounds.width - leftRightBorder * 2,
						unselectedHeightToDraw, defaultBackground,
						getUnselectedTabsColors(state),
						getUnselectedTabsPercents(state), vertical);
			}
		}
	}

	private Control findToolbarContainer() {
		Object obj = parent.getData(TOOLBAR_CONTAINER);
		if (obj instanceof Control) {
			Control control = (Control) obj;
			if (!control.isDisposed()) {
				return control;
			}
		}

		for (Control child : parent.getChildren()) {
			if (child instanceof Composite) {
				for (Control subChild : ((Composite) child).getChildren()) {
					if (subChild instanceof ToolBar && subChild.isVisible()) {
						parent.setData(TOOLBAR_CONTAINER, child);
						return child;
					}
				}
			}
		}
		return null;
