		final URIish uri = element.getUri();
		result.append(UIText.PushResultTable_repository);
		result.append(SPACE);
		result.append(uri.toString());
		result.append(Text.DELIMITER);
		result.append(Text.DELIMITER);
		String message = element.getRemoteRefUpdate().getMessage();
		if (message != null)
			result.append(message).append(Text.DELIMITER);
		StringBuilder messagesBuffer = new StringBuilder(pushOperationResult
				.getPushResult(uri).getMessages());
		trim(messagesBuffer);
		if (messagesBuffer.length() > 0)
			result.append(messagesBuffer).append(Text.DELIMITER);
