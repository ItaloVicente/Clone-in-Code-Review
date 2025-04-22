		TestProtocol<User> proto = registerProto(new UploadPackFactory<User>() {
			@Override
			public UploadPack create(User req, Repository db)
					throws ServiceNotAuthorizedException {
				if (!"user2".equals(req.name)) {
					rejected.incrementAndGet();
					throw new ServiceNotAuthorizedException();
				}
				return new UploadPack(db);
