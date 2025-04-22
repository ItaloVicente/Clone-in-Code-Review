		try {
			request.setAttribute(ATTRIBUTE_REPOSITORY, db);
			chain.doFilter(request, response);
		} finally {
			request.removeAttribute(ATTRIBUTE_REPOSITORY);
			db.close();
		}
