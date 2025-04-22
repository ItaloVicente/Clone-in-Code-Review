	public static MessageDialogWithToggle open(int kind, Shell parent, String title, String message,
			String toggleMessage, boolean toggleState, IPreferenceStore store, String key, int style,
			String[] buttonLabels, Integer[] buttonIds) {
		MessageDialogWithToggle dialog = new MessageDialogWithToggle(parent,
				title, null, message, kind, buttonLabels, buttonIds, 0, toggleMessage, false);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		dialog.prefStore = store;
		dialog.prefKey = key;
		dialog.open();
		return dialog;
	}

