		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob = remote2.blob("foo");
			RevTree tree = remote2.tree(remote2.file("1"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					false);

			testProtocol = new TestProtocol<>(new UploadPackFactory<Object>() {
				@Override
				public UploadPack create(Object req
						throws ServiceNotEnabledException
						ServiceNotAuthorizedException {
					UploadPack up = new UploadPack(db);
					return up;
				}
			}
			uri = testProtocol.register(ctx
