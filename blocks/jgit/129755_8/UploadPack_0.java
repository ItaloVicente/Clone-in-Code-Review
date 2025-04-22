		if (currentRequest == null) {
			return userAgent;
		}

		return UserAgent.getAgent(currentRequest.getClientCapabilities()
				userAgent);
