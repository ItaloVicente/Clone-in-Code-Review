	private int leftEdge(Control control) {
		Rectangle bounds = control.getBounds();

		return bounds.x;
	}

	private int bottomEdge(Control control) {
		Rectangle bounds = control.getBounds();

		return bounds.y + bounds.height;
