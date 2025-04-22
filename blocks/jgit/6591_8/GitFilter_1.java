	@SuppressWarnings("unchecked")
	public void setPublisherClientFactory(PublisherClientFactory<HttpServletRequest> f) {
		assertNotInitialized();
		this.publisherClientFactory = f != null ? f : PublisherClientFactory.DISABLED;
	}

	public void setPublisher(Publisher p) {
		this.publisher = p;
	}

	public void addPublisherClientFilter(Filter filter) {
		assertNotInitialized();
		publisherClientFilters.add(filter);
	}

