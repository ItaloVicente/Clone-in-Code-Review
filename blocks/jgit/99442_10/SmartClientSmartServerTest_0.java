	private ServletContextHandler addAuthContext(GitServlet gs
			String contextPath
		ServletContextHandler auth = server.addContext('/' + contextPath);
		auth.addServlet(new ServletHolder(gs)
		return server.authBasic(auth
	}

	private ServletContextHandler addRedirectContext(GitServlet gs) {
