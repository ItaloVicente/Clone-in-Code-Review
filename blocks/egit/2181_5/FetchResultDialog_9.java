		setTitle(NLS.bind(UIText.FetchResultDialog_labelNonEmptyResult,
				sourceString));

		if (result.getErrorMessage() != null)
			setErrorMessage(result.getErrorMessage());
		else if (result.getFetchResult() != null
				&& result.getFetchResult().getTrackingRefUpdates().isEmpty()) {
			setMessage(NLS.bind(UIText.FetchResultDialog_labelEmptyResult,
					sourceString));
		}
