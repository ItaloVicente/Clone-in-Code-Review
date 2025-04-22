	protected void service(final HttpServletRequest req,
			final HttpServletResponse rsp) throws ServletException, IOException {
		final UrlPipeline p = find(req);
		if (p != null)
			p.service(req, rsp);
		else
			rsp.sendError(SC_NOT_FOUND);
