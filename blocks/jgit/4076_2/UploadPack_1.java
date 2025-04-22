		if (!biDirectionalPipe && requestPolicy == RequestPolicy.ADVERTISED)
			requestPolicy = RequestPolicy.REACHABLE_COMMIT;
	}

	public RequestPolicy getRequestPolicy() {
		return requestPolicy;
	}

	public void setRequestPolicy(RequestPolicy policy) {
		requestPolicy = policy != null ? policy : RequestPolicy.ADVERTISED;
