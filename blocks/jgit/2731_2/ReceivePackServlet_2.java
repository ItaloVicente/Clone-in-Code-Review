		Factory(ReceivePackFactory<HttpServletRequest> receivePackFactory) {
			this.receivePackFactory = receivePackFactory;
		}

		public void doFilter(ServletRequest request
				FilterChain chain) throws IOException
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse rsp = (HttpServletResponse) response;
			ReceivePack rp;
			try {
				rp = receivePackFactory.create(req
			} catch (ServiceNotAuthorizedException e) {
				rsp.sendError(SC_UNAUTHORIZED);
				return;

			} catch (ServiceNotEnabledException e) {
				rsp.sendError(SC_FORBIDDEN);
				return;
			}

			try {
				req.setAttribute(ATTRIBUTE_HANDLER
				chain.doFilter(req
			} finally {
				req.removeAttribute(ATTRIBUTE_HANDLER);
			}
		}

		public void init(FilterConfig filterConfig) throws ServletException {
		}

		public void destroy() {
		}
