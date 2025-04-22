		app.addFilter(new FilterHolder(new Filter() {

			@Override
			public void init(FilterConfig filterConfig)
					throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request
					ServletResponse response
					throws IOException
				final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				final StringBuffer fullUrl = httpServletRequest.getRequestURL();
				if (httpServletRequest.getQueryString() != null) {
					fullUrl.append("?")
							.append(httpServletRequest.getQueryString());
				}
				String urlString = fullUrl.toString();
				if (urlString.contains("/post/")) {
					if ("POST"
							.equalsIgnoreCase(httpServletRequest.getMethod())) {
						httpServletResponse.setStatus(
								HttpServletResponse.SC_MOVED_PERMANENTLY);
						httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
								urlString.replace("/post/"
					} else {
						String path = httpServletRequest.getPathInfo();
						path = path.replace("/post/"
						if (httpServletRequest.getQueryString() != null) {
							path += '?' + httpServletRequest.getQueryString();
						}
						if (!path.startsWith("/")) {
							path = '/' + path;
						}
						RequestDispatcher dispatcher = httpServletRequest
								.getRequestDispatcher(path);
						dispatcher.forward(httpServletRequest
								httpServletResponse);
					}
					return;
				}
				chain.doFilter(httpServletRequest
			}

			@Override
			public void destroy() {
			}
		})
