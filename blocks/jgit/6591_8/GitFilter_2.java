		if (publisher == null
				&& publisherClientFactory != PublisherClientFactory.DISABLED) {
			publisher = new Publisher(new PublisherReverseResolver()
				new PublisherPackFactory(new PublisherBuffer(1 << 30))
			new SessionGenerator() {
				public String generate() {
					return UUID.randomUUID().toString();
				}
			});
		}

