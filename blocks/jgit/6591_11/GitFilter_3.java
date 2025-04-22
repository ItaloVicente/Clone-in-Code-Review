		if (publisherClientFactory != PublisherClientFactory.DISABLED) {
			publisherClientFactory.setPublisher(publisher);
			publisherClientFactory.setResolver(resolver);
			ServletBinder b = serveRegex(
					"^/" + GitSmartHttpTools.PUBLISH_SUBSCRIBE
			b = b.through(new PublisherServlet.Factory(publisherClientFactory));
			for (Filter f : publisherClientFilters)
				b = b.through(f);
			b.with(new PublisherServlet(publisher));
		}

