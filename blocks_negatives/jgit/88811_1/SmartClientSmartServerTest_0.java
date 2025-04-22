		ServletContextHandler broken = server.addContext("/bad");
		broken.addFilter(new FilterHolder(new Filter() {
			public void doFilter(ServletRequest request,
					ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				final HttpServletResponse r = (HttpServletResponse) response;
				r.setContentType("text/plain");
				r.setCharacterEncoding("UTF-8");
				PrintWriter w = r.getWriter();
				w.print("OK");
				w.close();
			}
