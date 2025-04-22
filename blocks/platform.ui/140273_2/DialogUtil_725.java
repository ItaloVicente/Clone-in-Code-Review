		return label;
	}

	public static int availableRows(Composite parent) {

		int fontHeight = (parent.getFont().getFontData())[0].getHeight();
		int displayHeight = parent.getDisplay().getClientArea().height;

		return displayHeight / fontHeight;
	}

	public static boolean inRegularFontMode(Composite parent) {

		return availableRows(parent) > 50;
	}
