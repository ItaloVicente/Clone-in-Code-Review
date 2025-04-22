	private void transportError(final Throwable why) {
		Throwable cause = why.getCause();
		if (why instanceof TransportException && cause != null)
			NLS.bind(UIText.SourceBranchPage_CompositeTransportErrorMessage,
					why.getMessage(), cause.getMessage());
		else
			transportError(why.getMessage());
	}

