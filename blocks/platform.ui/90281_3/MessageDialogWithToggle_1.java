	public MessageDialogWithToggle(Shell parentShell, String dialogTitle, Image image, String message,
			int dialogImageType, Map<String, Integer> buttonLabelToIdMap, int defaultIndex,
			String toggleMessage, boolean toggleState) {
		super(parentShell, dialogTitle, image, message, dialogImageType, defaultIndex,
				buttonLabelToIdMap.keySet().toArray(new String[buttonLabelToIdMap.size()]));
		this.toggleMessage = toggleMessage;
		this.toggleState = toggleState;
		this.dialogButtonIdMapping = buttonLabelToIdMap;
	}

