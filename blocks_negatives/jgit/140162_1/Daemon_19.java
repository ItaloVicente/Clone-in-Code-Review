		daemon.setReceivePackFactory(new ReceivePackFactory<DaemonClient>() {
			@Override
			public ReceivePack create(DaemonClient req, Repository repo)
					throws ServiceNotEnabledException,
					ServiceNotAuthorizedException {
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
			}
		});
