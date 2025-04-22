	private void drawChildrenBackground(Rectangle partHeaderBounds) {
		for (Control control : parent.getChildren()) {
			if (!hasBackgroundOverriddenByCSS(control) && containsToolbar(control)) {
				drawChildBackground((Composite) control, partHeaderBounds);
			}
		}
	}

	private boolean containsToolbar(Control control) {
		if (control.getData(CONTAINS_TOOLBAR) != null) {
			return true;
		}

		if (control instanceof Composite) {
			for (Control child : ((Composite) control).getChildren()) {
				if (child instanceof ToolBar) {
					control.setData(CONTAINS_TOOLBAR, true);
					return true;
				}
			}
		}
		return false;
	}

	private void drawChildBackground(Composite composite, Rectangle partHeaderBounds) {
		Rectangle rec = composite.getBounds();
		Color background = null;
		boolean partOfHeader = rec.y >= partHeaderBounds.y && rec.y < partHeaderBounds.height;

		if (!partOfHeader && selectedTabFillColors != null) {
			background = selectedTabFillColors.length == 2 ? selectedTabFillColors[1] : selectedTabFillColors[0];
		}

		setBackgroundOverriddenDuringRenderering(composite, background);
	}

