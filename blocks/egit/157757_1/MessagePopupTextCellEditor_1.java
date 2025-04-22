	private void drawRectangle(Event event) {
		Rectangle textBounds = text.getBounds();
		if (textBounds.x < 2 || textBounds.y < 1) {
			textBounds = resizeText();
		}
		Rectangle parentSize = outer.getClientArea();
		event.gc.setForeground(text.getForeground());
		event.gc.drawRectangle(0, 0,
				Math.min(textBounds.width + 4 - 1, parentSize.width - 1),
				parentSize.height - 1);
	}

	private Rectangle resizeText() {
		Rectangle area = outer.getClientArea();
		Point size = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		size.x += size.y;
		if (text.getText().isEmpty()) {
			size.x = size.y;
		}
		Rectangle bounds = new Rectangle(2, 1, Math.min(size.x, area.width - 4),
				area.height - 2);
		text.setBounds(bounds);
		return bounds;
	}

	private void adjustSize() {
		resizeText();
		outer.redraw();
	}

	@Override
	public void performDelete() {
		super.performDelete();
		if (outer != null) {
			adjustSize();
		}
	}

	@Override
	public void performPaste() {
		super.performPaste();
		text.showSelection();
		if (outer != null) {
			adjustSize();
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (outer != null) {
			outer.dispose();
			outer = null;
		}
	}

