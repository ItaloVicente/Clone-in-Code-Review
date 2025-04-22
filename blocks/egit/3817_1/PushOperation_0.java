		if (uri != null)
			operationResult.addOperationResult(uri, userMessage);
		String uriString;
		if (uri == null)
			uriString = "retrieving URI failed"; //$NON-NLS-1$
		else
			uriString = uri.toString();
		String userMessageForUri = NLS.bind(
				CoreText.PushOperation_ExceptionOccurredDuringPushOnUriMessage,
				uriString, userMessage);
