
	public static void setFont(Control control, Font font) {
		if (!equals(control.getFont(), font)) {
			storeDefaultFont(control);
			control.setFont(font);
		}
	}

	public static void setFont(CTabItem item, Font font) {
		if (!equals(item.getFont(), font)) {
			storeDefaultFont(item);
			item.setFont(font);
		}
	}

	public static boolean equals(Font f1, Font f2) {
		if (f1 == f2) {
			return true;
		}
		if (f1 == null || f2 == null) {
			return false;
		}
		if (f1.equals(f2)) {
			return true;
		}
		FontData[] fd1 = f1.getFontData();
		FontData[] fd2 = f2.getFontData();
		if (fd1.length != fd2.length) {
			return false;
		}
		for (int i = 0; i < fd1.length; i++) {
			if (!fd1[i].equals(fd2[i])) {
				return false;
			}
		}
		return true;
	}

