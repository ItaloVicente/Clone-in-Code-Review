			setRepositoryResolver(
					new FileResolver<HttpServletRequest>(root
		}

		if (publisher == null) {
			publisher = new Publisher(new PublisherReverseResolver()
				new RepositoryResolver<PublisherClient>() {
				public Repository open(PublisherClient req
						throws RepositoryNotFoundException
						ServiceNotAuthorizedException
						ServiceNotEnabledException
						ServiceMayNotContinueException {
					HttpServletRequest request = ((PublisherClientConnectionWrap) req).getRequest();
					return resolver.open(request
				}
			}
				public String generate() {
					return UUID.randomUUID().toString();
				}
			});
