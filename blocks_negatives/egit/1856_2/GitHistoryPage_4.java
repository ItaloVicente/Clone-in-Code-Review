		if (this.input != null)
			return true;

		cancelRefreshJob();
		setErrorMessage(null);
		Object o = super.getInput();
		if (o == null) {
			setErrorMessage(UIText.GitHistoryPage_NoInputMessage);
			return false;
		}
