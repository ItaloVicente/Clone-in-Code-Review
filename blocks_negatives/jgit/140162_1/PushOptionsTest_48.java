		testProtocol = new TestProtocol<>(null,
				new ReceivePackFactory<Object>() {
					@Override
					public ReceivePack create(Object req, Repository git)
							throws ServiceNotEnabledException,
							ServiceNotAuthorizedException {
						receivePack = new ReceivePack(git);
						receivePack.setAllowPushOptions(true);
						receivePack.setAtomic(true);
						return receivePack;
					}
				});
