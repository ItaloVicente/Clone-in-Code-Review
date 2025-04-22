	@Nullable
	public String getClientSID() {
		if (currentRequest == null) {
			return null;
		}

		return currentRequest.getClientSID();
	}

