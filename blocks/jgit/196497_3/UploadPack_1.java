	public String getClientSID() {
		if (currentRequest != null
				&& currentRequest.getClientSID() != null) {
			return currentRequest.getClientSID();
		}

		return null;
	}

