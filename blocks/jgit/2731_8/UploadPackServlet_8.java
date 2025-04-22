	static class Factory implements Filter {
		private final UploadPackFactory<HttpServletRequest> uploadPackFactory;

		Factory(UploadPackFactory<HttpServletRequest> uploadPackFactory) {
			this.uploadPackFactory = uploadPackFactory;
		}

		public void doFilter(ServletRequest request
				FilterChain chain) throws IOException
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse rsp = (HttpServletResponse) response;
			UploadPack rp;
			try {
				rp = uploadPackFactory.create(req
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
