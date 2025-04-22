	public ConnectionFactoryBuilder setMaxFrontCacheElements(int to) {
		assert to > 0 : "In case of front cache, the number must be a positive number";
		maxFrontCacheElements = to;
		return this;
	}

	public ConnectionFactoryBuilder setFrontCacheExpireTime(int to) {
		assert to > 0 : "Front cache's expire time must be a positive number";
		frontCacheExpireTime = to;
		return this;
	}

