
	/** Simplify testing for color equality */
	public static boolean equals(Color c1, Color c2) {
		if (c1 == c2) {
			return true;
		}
		if (c1 == null || c2 == null) {
			return false;
		}
		return c1.equals(c2);
	}

	/** Helper function to avoid setting colors unnecessarily */
	public static void setForeground(Control control, Color newColor) {
		if (!equals(control.getForeground(), newColor)) {
			control.setForeground(newColor);
		}
	}

	/** Helper function to avoid setting colors unnecessarily */
	public static void setBackground(Control control, Color newColor) {
		if (!equals(control.getBackground(), newColor)) {
			control.setBackground(newColor);
		}
	}

	/** Helper function to avoid setting colors unnecessarily */
	public static void setSelectionForeground(CTabFolder folder, Color newColor) {
		if (!equals(folder.getSelectionForeground(), newColor)) {
			folder.setSelectionForeground(newColor);
		}
	}

	/** Helper function to avoid setting colors unnecessarily */
	public static void setSelectionBackground(CTabFolder folder, Color newColor) {
		if (!equals(folder.getSelectionBackground(), newColor)) {
			folder.setSelectionBackground(newColor);
		}
	}
