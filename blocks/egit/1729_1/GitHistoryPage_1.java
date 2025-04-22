		if (graph == null)
			return false;

		setErrorMessage(null);
		if (super.getInput() == null) {
			setErrorMessage(UIText.GitHistoryPage_NoInputMessage);
