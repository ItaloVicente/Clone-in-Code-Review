		TestProtocol<User> proto = registerProto(new DefaultUpload(),
				new ReceivePackFactory<User>() {
					@Override
					public ReceivePack create(User req, Repository db)
							throws ServiceNotAuthorizedException {
						if (!"user2".equals(req.name)) {
							rejected.incrementAndGet();
							throw new ServiceNotAuthorizedException();
						}
						return new ReceivePack(db);
					}
				});
