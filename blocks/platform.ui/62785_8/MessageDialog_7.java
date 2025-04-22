	public static boolean open(int kind, Shell parent, String title, String message, int style,
			SelectionListener[] linkListeners) {
		MessageDialog dialog = new MessageDialog(parent, title, null, message, kind, 0, getButtonLabels(kind),
				linkListeners);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		return dialog.open() == 0;
	}

