	public static MessageDialogWithToggle open(int kind, Shell parent, String title, String message,
			String toggleMessage, boolean toggleState, IPreferenceStore store, String key, int style,
			Map<String, Integer> buttonLabelToIdMap) {
		MessageDialogWithToggle dialog = new MessageDialogWithToggle(parent, title, null, message, kind,
				buttonLabelToIdMap, 0, toggleMessage, toggleState);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		dialog.prefStore = store;
		dialog.prefKey = key;
		dialog.open();
		return dialog;
	}

