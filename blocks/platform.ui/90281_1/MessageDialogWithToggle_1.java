	public MessageDialogWithToggle(Shell parentShell, String dialogTitle, Image image, String message,
			int dialogImageType, String[] dialogButtonLabels, Integer[] dialogButtonIds, int defaultIndex,
			String toggleMessage, boolean toggleState) {
		super(parentShell, dialogTitle, image, message, dialogImageType, defaultIndex, dialogButtonLabels);
		this.toggleMessage = toggleMessage;
		this.toggleState = toggleState;
		setButtonLabels(dialogButtonLabels);

		if (dialogButtonLabels.length != dialogButtonIds.length) {
			throw new IllegalArgumentException();
		}

		dialogButtonIdMapping = new Hashtable<>();
		for (int i = 0; i < dialogButtonLabels.length; ++i) {
			dialogButtonIdMapping.put(dialogButtonLabels[i], dialogButtonIds[i]);
		}
	}

