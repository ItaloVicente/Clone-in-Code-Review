		try (Repository db = resolver.open(req
			try {
				request.setAttribute(ATTRIBUTE_REPOSITORY
				chain.doFilter(request
			} finally {
				request.removeAttribute(ATTRIBUTE_REPOSITORY);
			}
