		setErrorMessage(null);
		if (super.getInput() == null) {
			setErrorMessage(UIText.GitHistoryPage_NoInputMessage);
			return false;
		}

