	private String getMessage(final Throwable why) {
		if (why.getMessage().endsWith("Auth fail")) //$NON-NLS-1$
			return UIText.SourceBranchPage_AuthFailMessage;
		else
			return UIText.SourceBranchPage_CompositeTransportErrorMessage;
	}

