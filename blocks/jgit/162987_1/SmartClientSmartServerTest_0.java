	private ServletContextHandler addSlowContext(GitServlet gs
			boolean auth) {
		ServletContextHandler slow = server.addContext('/' + path);
		slow.addFilter(new FilterHolder(new Filter() {

			@Override
			public void init(FilterConfig filterConfig)
					throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request
					ServletResponse response
					throws IOException
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					throw new IOException(e);
				}
				chain.doFilter(request
			}

			@Override
			public void destroy() {
			}
		})
		slow.addServlet(new ServletHolder(gs)
		if (auth) {
			return server.authBasic(slow);
		}
		return slow;
	}

