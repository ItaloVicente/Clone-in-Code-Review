		final String[] buttonLabels = getButtonLabels();
		final Button[] buttons = new Button[buttonLabels.length];
		final int defaultButtonIndex = getDefaultButtonIndex();

		int suggestedId = IDialogConstants.INTERNAL_ID;
		for (int i = 0; i < buttonLabels.length; i++) {
			String label = buttonLabels[i];
			int id = mapButtonLabelToButtonID(label, suggestedId);

			if (id == suggestedId) {
