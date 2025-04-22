	}

	private void drawChildrenBackground(Rectangle partHeaderBounds) {
		for (Control control : parent.getChildren()) {
			if (control instanceof Composite
					&& !hasBackgroundOverriddenByCSS(control)) {
				drawChildBackground((Composite) control, partHeaderBounds);
			}
		}
	}

	private void drawChildBackground(Composite composite,
			Rectangle partHeaderBounds) {
		Rectangle rec = composite.getBounds();
		Color background = null;
		boolean partOfHeader = rec.y >= partHeaderBounds.y
				&& rec.y < partHeaderBounds.height;

		if (!partOfHeader && selectedTabFillColors != null) {
			background = selectedTabFillColors.length == 2 ? selectedTabFillColors[1]
						: selectedTabFillColors[0];
		}
