	@Override
	protected void service(HttpServletRequest req
			throws ServletException
		filter.doFilter(req
			public void doFilter(ServletRequest request
					ServletResponse response) throws IOException
					ServletException {
				((HttpServletResponse) response).sendError(SC_NOT_FOUND);
			}
		});
