	public MessageDialogWithToggle(Shell parentShell, String dialogTitle,
			Image image, String message, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex,
			String toggleMessage, boolean toggleState) {
		super(parentShell, dialogTitle, image, message, dialogImageType, defaultIndex, dialogButtonLabels);
		this.toggleMessage = toggleMessage;
		this.toggleState = toggleState;
		setButtonLabels(dialogButtonLabels);
	}

