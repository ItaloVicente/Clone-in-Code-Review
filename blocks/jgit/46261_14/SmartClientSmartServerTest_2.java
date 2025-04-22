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
				if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
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
					RequestDispatcher dispatcher = httpServletRequest
							.getRequestDispatcher(path);
					dispatcher.forward(httpServletRequest
				}
			}

			@Override
			public void destroy() {
			}
		})
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
				urlString = "https" + urlString.substring(schemeEnd);
				String port = ":" + server.getPort();
				urlString = urlString.replace(port
						":" + server.getSecurePort());
				System.out.println(urlString);
				httpServletResponse
						.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
						urlString.replace("/https/"
			}

			@Override
			public void destroy() {
			}
		})
		gs.setRepositoryResolver(new TestRepositoryResolver(src
