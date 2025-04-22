		repositoryResolver = (RepositoryResolver<DaemonClient>) RepositoryResolver.NONE;

		uploadPackFactory = new UploadPackFactory<DaemonClient>() {
			public UploadPack create(DaemonClient req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				UploadPack up = new UploadPack(db);
				up.setTimeout(getTimeout());
				up.setPackConfig(getPackConfig());
				return up;
			}
		};

		receivePackFactory = new ReceivePackFactory<DaemonClient>() {
			public ReceivePack create(DaemonClient req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack rp = new ReceivePack(db);

				InetAddress peer = req.getRemoteAddress();
				String host = peer.getCanonicalHostName();
				if (host == null)
					host = peer.getHostAddress();
				String name = "anonymous";
				String email = name + "@" + host;
				rp.setRefLogIdent(new PersonIdent(name
				rp.setTimeout(getTimeout());

				return rp;
			}
		};

