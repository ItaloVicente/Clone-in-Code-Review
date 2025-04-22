	public long getFilterBlobLimit() {
		if (currentRequest == null) {
			throw new RequestNotYetReadException();
		}
		return currentRequest.getFilterBlobLimit();
	}

