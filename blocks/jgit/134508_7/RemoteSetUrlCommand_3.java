		if (push) {
			setUriType(UriType.PUSH);
		} else {
			setUriType(UriType.FETCH);
		}
	}

	public RemoteSetUrlCommand setUriType(UriType type) {
		this.type = type;
		return this;
