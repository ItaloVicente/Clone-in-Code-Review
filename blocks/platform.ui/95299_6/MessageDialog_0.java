	public static int open(int kind, Shell parent, String title, String message, int style,
			String... dialogButtonLabels) {
		MessageDialog dialog = new MessageDialog(parent, title, null, message, kind, 0, dialogButtonLabels);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		return dialog.open();
	}

