	public static MessageDialogWithToggle open(int kind, Shell parent, String title,
			String message, String toggleMessage, boolean toggleState,
			IPreferenceStore store, String key, int style) {
		MessageDialogWithToggle dialog = new MessageDialogWithToggle(parent,
				title, null, // accept the default window icon
				message, kind, getButtonLabels(kind), 0,
				toggleMessage, toggleState);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		dialog.prefStore = store;
		dialog.prefKey = key;
		dialog.open();
		return dialog;
	}

