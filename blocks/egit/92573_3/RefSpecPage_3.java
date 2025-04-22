	public FetchRecurseSubmodulesMode getFetchRecurseSubmodulesMode() {
		if (recurseSubmodulesYesButton.getSelection()) {
			return FetchRecurseSubmodulesMode.YES;
		}
		if (recurseSubmodulesNoButton.getSelection()) {
			return FetchRecurseSubmodulesMode.NO;
		}

		return FetchRecurseSubmodulesMode.ON_DEMAND;
	}

