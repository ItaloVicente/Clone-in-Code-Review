		if (receivePackFactory != ReceivePackFactory.DISABLED) {
					.with(new ReceivePackServlet(receivePackFactory));
		}

		if (receivePackFactory != ReceivePackFactory.DISABLED) {
					new ReceivePackServlet.InfoRefs(receivePackFactory));
		}
		if (getAnyFile != GetAnyFile.DISABLED) {
			refs = refs.through(new GetAnyFileFilter(getAnyFile));
			refs.with(new InfoRefsServlet());
		} else {
			refs.with(new HttpServlet() {
				private static final long serialVersionUID = 1L;

				@Override
				protected void doGet(HttpServletRequest req
						HttpServletResponse rsp) throws ServletException
						IOException {
					rsp.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			});
		}

