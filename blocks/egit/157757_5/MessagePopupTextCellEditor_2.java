
	private class BorderLayout extends Layout {

		@Override
		protected Point computeSize(Composite composite, int wHint, int hHint,
				boolean flushCache) {
			Point inner = computeTextSize();
			if (wHint == SWT.DEFAULT) {
				inner.x += 4;
			} else {
				inner.x = wHint;
			}
			if (hHint != SWT.DEFAULT) {
				inner.y = hHint;
			}
			return inner;
		}

		@Override
		protected void layout(Composite composite, boolean flushCache) {
			resizeText();
		}
	}
