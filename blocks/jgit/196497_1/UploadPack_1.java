	public String getClientSessionID() {
		if (currentRequest != null
				&& currentRequest.getClientSessionID() != null) {
			return currentRequest.getClientSessionID();
		}

		return null;
	}

