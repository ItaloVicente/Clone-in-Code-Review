		uploadPackFactory = new UploadPackFactory<DaemonClient>() {
			@Override
			public UploadPack create(DaemonClient req, Repository db)
					throws ServiceNotEnabledException,
					ServiceNotAuthorizedException {
				UploadPack up = new UploadPack(db);
				up.setTimeout(getTimeout());
				up.setPackConfig(getPackConfig());
				return up;
			}
		};

		receivePackFactory = new ReceivePackFactory<DaemonClient>() {
			@Override
			public ReceivePack create(DaemonClient req, Repository db)
					throws ServiceNotEnabledException,
					ServiceNotAuthorizedException {
				ReceivePack rp = new ReceivePack(db);

				InetAddress peer = req.getRemoteAddress();
				String host = peer.getCanonicalHostName();
				if (host == null)
					host = peer.getHostAddress();
				rp.setRefLogIdent(new PersonIdent(name, email));
				rp.setTimeout(getTimeout());

				return rp;
			}
		};
