		Set<URIish> urIs = pushOperationResult.getURIs();
		Iterator<URIish> iterator = urIs.iterator();
		while(iterator.hasNext()) {
			boolean lineBreakNeeded = false;
			URIish uri = iterator.next();
			result.append(UIText.PushResultTable_repository);
			result.append(SPACE);
			result.append(uri.toString());
			result.append(Text.DELIMITER);
			result.append(Text.DELIMITER);
			String message = element.getRemoteRefUpdate(uri).getMessage();
			if (message != null) {
				result.append(message);
				result.append(Text.DELIMITER);
				lineBreakNeeded = true;
			}
			StringBuilder messagesBuffer = new StringBuilder(pushOperationResult
					.getPushResult(uri).getMessages());
			trim(messagesBuffer);
			if (messagesBuffer.length()>0) {
				result.append(messagesBuffer);
				result.append(Text.DELIMITER);
				lineBreakNeeded = true;
			}
			if (iterator.hasNext() && lineBreakNeeded)
				result.append(Text.DELIMITER);
		}
