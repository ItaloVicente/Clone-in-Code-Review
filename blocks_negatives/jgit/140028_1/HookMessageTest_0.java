		final TestRepository<Repository> src = createTestRepository();
		final String srcName = src.getRepository().getDirectory().getName();

		ServletContextHandler app = server.addContext("/git");
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req, String name)
					throws RepositoryNotFoundException,
					ServiceNotEnabledException {
				if (!name.equals(srcName))
					throw new RepositoryNotFoundException(name);

				final Repository db = src.getRepository();
				db.incrementOpen();
				return db;
			}
		});
		gs.setReceivePackFactory(new DefaultReceivePackFactory() {
			@Override
			public ReceivePack create(HttpServletRequest req, Repository db)
					throws ServiceNotEnabledException,
					ServiceNotAuthorizedException {
				ReceivePack recv = super.create(req, db);
				recv.setPreReceiveHook(new PreReceiveHook() {
					@Override
					public void onPreReceive(ReceivePack rp,
							Collection<ReceiveCommand> commands) {
						rp.sendMessage("message line 1");
						rp.sendError("no soup for you!");
						rp.sendMessage("come back next year!");
					}
				});
				return recv;
			}

		});
		app.addServlet(new ServletHolder(gs), "/*");

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app, srcName);

		StoredConfig cfg = remoteRepository.getConfig();
		cfg.setBoolean("http", null, "receivepack", true);
		cfg.save();
