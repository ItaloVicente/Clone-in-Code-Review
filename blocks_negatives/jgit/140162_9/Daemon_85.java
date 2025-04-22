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
