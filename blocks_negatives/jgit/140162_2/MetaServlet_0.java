		filter.doFilter(req, res, new FilterChain() {
			@Override
			public void doFilter(ServletRequest request,
					ServletResponse response) throws IOException,
					ServletException {
				((HttpServletResponse) response).sendError(SC_NOT_FOUND);
			}
		});
