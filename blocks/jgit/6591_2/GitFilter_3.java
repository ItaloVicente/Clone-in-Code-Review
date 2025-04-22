		if (publisherClientFactory != PublisherClientFactory.DISABLED) {
			publisherClientFactory.setPublisher(publisher);
			ServletBinder b = serveRegex(
					"^/" + GitSmartHttpTools.PUBLISH_SUBSCRIBE
			b = b.through(new PublisherServlet.Factory(publisherClientFactory));
			for (Filter f : publisherClientFilters)
				b = b.through(f);
			b.with(new PublisherServlet());

			ServletBinder pubRefs = serveRegex("^/" + Constants.INFO_REFS
					true);
			pubRefs = pubRefs.through(new PublisherServlet.InfoRefs(
					publisherClientFilters));
			pubRefs.with(new ErrorServlet(HttpServletResponse.SC_FORBIDDEN));
		}

