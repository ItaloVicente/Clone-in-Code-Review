		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req, Repository db)
							throws ServiceNotEnabledException,
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
						up.getRevWalk().assumeShallow(
								Collections.singleton(commit1.getId()));
						return up;
					}
				}, null);
