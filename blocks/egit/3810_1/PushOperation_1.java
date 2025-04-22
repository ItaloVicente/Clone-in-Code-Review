
	private void handleException(final URIish uri, Exception e,
			String userMessage) {
		operationResult.addOperationResult(uri, userMessage);
		String userMessageForUri = NLS.bind(CoreText.PushOperation_ExceptionOccurredDuringPushOnUriMessage, uri, userMessage);
		Activator.logError(userMessageForUri, e);
	}

