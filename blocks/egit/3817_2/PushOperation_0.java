		String uriString;
		if (uri != null) {
			operationResult.addOperationResult(uri, userMessage);
			uriString = uri.toString();
		} else
			uriString = "retrieving URI failed"; //$NON-NLS-1$

		String userMessageForUri = NLS.bind(
				CoreText.PushOperation_ExceptionOccurredDuringPushOnUriMessage,
				uriString, userMessage);
