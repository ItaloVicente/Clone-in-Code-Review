			Control control = (Control) widget;
			try {
				control.setRedraw(false);
				control.setFont(font);
				if (control instanceof CTabFolder) {
					updateChildrenFonts((CTabFolder) widget, font);
				}
			} finally {
				control.setRedraw(true);
			}
		}
	}

	private static void updateChildrenFonts(CTabFolder folder, Font font) {
		for (CTabItem item : folder.getItems()) {
			Font itemFont = item.getFont();
			if (itemFont != null && !itemFont.equals(font)) {
				item.setFont(font);
			}
