	private class DenmarkEntry extends CountryEntry {
		DenmarkEntry() {
			super("Danmark", "Denmark", "TBD");
		}

		@Override
		protected void drawFlag(Event event) {

			Rectangle bounds = event.getBounds();
			bounds.width += 100;

			event.gc.setBackground(viewer.getControl().getDisplay().getSystemColor(SWT.COLOR_RED));
			event.gc.fillRectangle(bounds);

			event.gc.setBackground(viewer.getControl().getDisplay().getSystemColor(SWT.COLOR_WHITE));
			event.gc.fillRectangle(new Rectangle(bounds.width / 2 + bounds.x - 5, bounds.y, 10, bounds.height));
			event.gc.fillRectangle(new Rectangle(bounds.x, bounds.height / 2 + bounds.y - 5, bounds.width, 10));
		}
	}

