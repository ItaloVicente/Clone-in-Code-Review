	private UrlPipeline find(final HttpServletRequest req)
			throws ServletException {
		for (UrlPipeline p : getPipelines())
			if (p.match(req))
				return p;
		return null;
