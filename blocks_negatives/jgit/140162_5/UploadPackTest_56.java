			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req, Repository db)
						throws ServiceNotEnabledException,
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}, null);
