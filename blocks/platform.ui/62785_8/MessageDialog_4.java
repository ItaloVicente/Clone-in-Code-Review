		this.titleImage = dialogTitleImage;
		this.message = dialogMessage;
		if (linkListeners != null) {
			for (SelectionListener listener : linkListeners) {
				this.addLinkSelectionListener(listener);
			}
		}

		switch (dialogImageType) {
		case ERROR: {
			this.image = getErrorImage();
			break;
		}
		case INFORMATION: {
			this.image = getInfoImage();
			break;
		}
		case QUESTION:
		case QUESTION_WITH_CANCEL:
		case CONFIRM: {
			this.image = getQuestionImage();
			break;
		}
		case WARNING: {
			this.image = getWarningImage();
			break;
		}
		}
		this.buttonLabels = dialogButtonLabels;
		this.defaultButtonIndex = defaultIndex;
