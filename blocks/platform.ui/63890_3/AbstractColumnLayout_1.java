		if (adjustForScrollBar) {
			ScrollBar verticalBar = scrollable.getVerticalBar();
			if (verticalBar != null && scrollable.getScrollbarsMode() == SWT.NONE && !verticalBar.isVisible()) {
				fixedWidth += verticalBar.getSize().x;
			}
		}
