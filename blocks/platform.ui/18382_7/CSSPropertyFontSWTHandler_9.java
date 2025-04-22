			if (widget instanceof CTabFolder) {
				updateChildrenFonts((CTabFolder) widget, font);
			}
		}
	}

	private static void updateChildrenFonts(CTabFolder folder, Font font) {
		for (CTabItem item : folder.getItems()) {
			Font itemFont = item.getFont();
			if (itemFont == null || itemFont.isDisposed()) {
				item.setFont(font);
			}
