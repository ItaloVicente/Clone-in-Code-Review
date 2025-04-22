		if (receivePackFactory != ReceivePackFactory.DISABLED) {
					.with(new ReceivePackServlet(receivePackFactory));
		}

		if (receivePackFactory != ReceivePackFactory.DISABLED) {
					new ReceivePackServlet.InfoRefs(receivePackFactory));
		}
		if (asIs != AsIsFileService.DISABLED) {
			refs = refs.through(new IsLocalFilter());
			refs = refs.through(new AsIsFileFilter(asIs));
			refs.with(new InfoRefsServlet());
		} else
			refs.with(new ErrorServlet(HttpServletResponse.SC_FORBIDDEN));

