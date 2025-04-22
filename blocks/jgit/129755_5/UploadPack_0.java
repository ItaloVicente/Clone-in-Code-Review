		Set<String> clientCapabilities = currentRequest == null ? null
				: currentRequest.getClientCapabilities();

		if (currentRequest == null) {
			return userAgent;
		}
		return UserAgent.getAgent(clientCapabilities
