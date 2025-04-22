	public static boolean open(int kind, Shell parent, String title, String message, int style, int defaultIndex,
			String[] buttonLabels, Image image) {
		buttonLabels = buttonLabels != null ? buttonLabels : getButtonLabels(kind);

		MessageDialog dialog = new MessageDialog(parent, title, null, message, kind, defaultIndex,
				buttonLabels);

		dialog.setShellStyle(dialog.getShellStyle() | style);
		if (image != null) {
			dialog.image = image;
		}
		return dialog.open() == 0;
	}

