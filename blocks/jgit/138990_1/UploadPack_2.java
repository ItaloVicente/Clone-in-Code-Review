	public FilterSpec getFilterSpec() {
		if (currentRequest == null) {
			throw new RequestNotYetReadException();
		}
		return currentRequest.getFilterSpec();
	}

