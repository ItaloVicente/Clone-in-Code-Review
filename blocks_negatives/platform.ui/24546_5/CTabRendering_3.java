	private void drawChildrenBackground(Rectangle partHeaderBounds) {
		for (Control control : parent.getChildren()) {
			if (control instanceof Composite
					&& !hasBackgroundOverriddenByCSS(control)) {
				drawChildBackground((Composite) control, partHeaderBounds);
			}
		}
