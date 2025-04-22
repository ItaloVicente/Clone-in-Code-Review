	private void drawRectangle(Event event) {
		Rectangle textBounds = text.getBounds();
		Rectangle parentSize = outer.getClientArea();
		event.gc.setForeground(text.getForeground());
		event.gc.drawRectangle(0, 0,
				Math.min(textBounds.width + 4, parentSize.width) - 1,
				parentSize.height - 1);
	}

	private Point computeTextSize() {
		boolean isEmpty = text.getText().isEmpty();
		Point size = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (isEmpty) {
			size.x = size.y;
		} else {
			size.x += size.y;
		}
		return size;
	}

	private void resizeText() {
		Rectangle area = outer.getClientArea();
		Point size = computeTextSize();
		text.setBounds(2, 1, Math.min(size.x, area.width - 4), area.height - 2);
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

