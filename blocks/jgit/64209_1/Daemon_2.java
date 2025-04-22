
	private void startKetchLeader(org.eclipse.jgit.transport.Daemon daemon) {
		KetchSystem system = new KetchSystem();
		final KetchLeaderCache leaders = new KetchLeaderCache(system);
		final ReceivePackFactory<DaemonClient> factory;

		factory = daemon.getReceivePackFactory();
		daemon.setReceivePackFactory(new ReceivePackFactory<DaemonClient>() {
			@Override
			public ReceivePack create(DaemonClient req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack rp = factory.create(req
				KetchLeader leader;
				try {
					leader = leaders.get(null
				} catch (URISyntaxException err) {
					throw new ServiceNotEnabledException(
							KetchText.get().invalidFollowerUri
				}
				rp.setPreReceiveHook(new KetchPreReceive(leader));
				return rp;
			}
		});
	}
