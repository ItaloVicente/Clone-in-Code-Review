	public static MessageDialogWithToggle openError(Shell parent, String title,
			String message, String toggleMessage, boolean toggleState,
			IPreferenceStore store, String key) {
		return open(ERROR, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
	}

	public static MessageDialogWithToggle openInformation(Shell parent,
			String title, String message, String toggleMessage,
			boolean toggleState, IPreferenceStore store, String key) {
		return open(INFORMATION, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
	}

	public static MessageDialogWithToggle openOkCancelConfirm(Shell parent,
			String title, String message, String toggleMessage,
			boolean toggleState, IPreferenceStore store, String key) {
		return open(CONFIRM, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
	}

	public static MessageDialogWithToggle openWarning(Shell parent,
			String title, String message, String toggleMessage,
			boolean toggleState, IPreferenceStore store, String key) {
		return open(WARNING, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
	}

	public static MessageDialogWithToggle openYesNoCancelQuestion(Shell parent,
			String title, String message, String toggleMessage,
			boolean toggleState, IPreferenceStore store, String key) {
		return open(QUESTION_WITH_CANCEL, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
	}

	public static MessageDialogWithToggle openYesNoQuestion(Shell parent,
			String title, String message, String toggleMessage,
			boolean toggleState, IPreferenceStore store, String key) {
		return open(QUESTION, parent, title, message, toggleMessage, toggleState, store, key, SWT.NONE);
	}

	private String prefKey = null;

	private IPreferenceStore prefStore = null;

	private Button toggleButton = null;

	private String toggleMessage;

	private boolean toggleState;
