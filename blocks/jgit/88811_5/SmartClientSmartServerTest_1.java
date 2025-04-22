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
				httpServletResponse
						.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
						fullUrl.toString().replace("/redirect"
			}
