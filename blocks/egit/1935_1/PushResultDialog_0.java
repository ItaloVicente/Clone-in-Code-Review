
	private String getPushErrors() {
		StringBuilder messages = new StringBuilder();
		for (URIish uri : result.getURIs()) {
			String errorMessage = result.getErrorMessage(uri);
			if (errorMessage != null && errorMessage.length() > 0) {
				if (messages.length() > 0)
					messages.append(System.getProperty("line.separator")); //$NON-NLS-1$
				messages.append(errorMessage);
			}
		}
		return messages.toString();
	}
