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
			item.setFont(font);
