
	private void startKetchLeader(org.eclipse.jgit.transport.Daemon daemon) {
		KetchSystem system = new KetchSystem();
		final KetchLeaderCache leaders = new KetchLeaderCache(system);
		final ReceivePackFactory<DaemonClient> factory;

		factory = daemon.getReceivePackFactory();
		daemon.setReceivePackFactory((DaemonClient req, Repository repo) -> {
			ReceivePack rp = factory.create(req, repo);
			KetchLeader leader;
			try {
				leader = leaders.get(repo);
			} catch (URISyntaxException err) {
				throw new ServiceNotEnabledException(
						KetchText.get().invalidFollowerUri, err);
			}
			rp.setPreReceiveHook(new KetchPreReceive(leader));
			return rp;
		});
	}
