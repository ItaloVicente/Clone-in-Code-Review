	private ServletContextHandler addNormalContext(GitServlet gs
		ServletContextHandler app = server.addContext("/git");

		gs.setRepositoryResolver(new TestRepoResolver(src
		app.addServlet(new ServletHolder(gs)
		return app;
	}

	private ServletContextHandler addBrokenContext(GitServlet gs
		ServletContextHandler broken = server.addContext("/bad");
		broken.addFilter(new FilterHolder(new Filter() {
					public void doFilter(ServletRequest request
										 ServletResponse response
							throws IOException
						final HttpServletResponse r = (HttpServletResponse) response;
						r.setContentType("text/plain");
						r.setCharacterEncoding("UTF-8");
						PrintWriter w = r.getWriter();
						w.print("OK");
						w.close();
					}

					public void init(FilterConfig filterConfig) throws ServletException {
					}

					public void destroy() {
					}
				})
				EnumSet.of(DispatcherType.REQUEST));
		broken.addServlet(new ServletHolder(gs)
		return broken;
	}

	private ServletContextHandler addRedirectContext(GitServlet gs
		ServletContextHandler redirect = server.addContext("/redirect");
		redirect.addFilter(new FilterHolder(new Filter() {
			@Override
			public void init(FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request
								 ServletResponse response
				final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				final StringBuffer fullUrl = httpServletRequest.getRequestURL();
				if (httpServletRequest.getQueryString() != null) {
					fullUrl.append("?").append(httpServletRequest.getQueryString());
				}
				httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
			}

			@Override
			public void destroy() {

			}
		})
		redirect.addServlet(new ServletHolder(gs)
		return redirect;
	}

