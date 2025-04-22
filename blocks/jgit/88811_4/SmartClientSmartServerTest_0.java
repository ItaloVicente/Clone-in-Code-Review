
		ServletContextHandler app = addNormalContext(gs

		ServletContextHandler broken = addBrokenContext(gs

		ServletContextHandler redirect = addRedirectContext(gs

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app
		brokenURI = toURIish(broken
		redirectURI = toURIish(redirect

		A_txt = src.blob("A");
		A = src.commit().add("A_txt"
		B = src.commit().parent(A).add("A_txt"
		src.update(master

		src.update("refs/garbage/a/very/long/ref/name/to/compress"
	}

	private ServletContextHandler addNormalContext(GitServlet gs
		ServletContextHandler app = server.addContext("/git");
