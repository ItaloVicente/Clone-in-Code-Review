
	public static void storeDefaultFont(Control control) {
		storeDefaultFont(control, control.getFont());
	}

	public static void storeDefaultFont(CTabItem item) {
		storeDefaultFont(item, item.getFont());
	}

	private static void storeDefaultFont(Widget widget, Font font) {
		if (widget.getData(DEFAULT_FONT) == null) {
			widget.setData(DEFAULT_FONT, font);
		}
	}

	public static void restoreDefaultFont(Control control) {
		Font defaultFont = (Font) control.getData(DEFAULT_FONT);
		if (defaultFont != null) {
			control.setFont(defaultFont.isDisposed() ? control.getDisplay()
					.getSystemFont() : defaultFont);
		}
	}

	public static void restoreDefaultFont(CTabItem item) {
		Font defaultFont = (Font) item.getData(DEFAULT_FONT);
		if (defaultFont != null) {
			item.setFont(defaultFont.isDisposed() ? item.getDisplay()
					.getSystemFont() : defaultFont);
		}
	}
