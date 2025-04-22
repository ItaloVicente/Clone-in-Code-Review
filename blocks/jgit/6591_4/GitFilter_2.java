		if (publisher == null
				&& publisherClientFactory != PublisherClientFactory.DISABLED) {
			publisher = new Publisher(new PublisherReverseResolver()
				new RepositoryResolver<PublisherClient>() {
				public Repository open(PublisherClient req
						throws RepositoryNotFoundException
						ServiceNotAuthorizedException
						ServiceNotEnabledException
						ServiceMayNotContinueException {
					HttpServletRequest request = ((PublisherClientConnectionWrapper) req).getRequest();
					return resolver.open(request
				}
			}
			new SessionGenerator() {
				public String generate() {
					return UUID.randomUUID().toString();
				}
			});
		}

