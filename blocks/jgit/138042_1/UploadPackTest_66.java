		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob1 = remote2.blob("foobar");
			RevBlob blob2 = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"
			remote2.update("a_blob"

			server2.getConfig().setBoolean("uploadpack"
					true);

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

			try (Transport tn = testProtocol.open(uri
				tn.setFilterBlobLimit(0);
				tn.fetch(NullProgressMonitor.INSTANCE
						new RefSpec(commit.name())
				assertTrue(client.getObjectDatabase().has(tree.toObjectId()));
				assertTrue(client.getObjectDatabase().has(blob1.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob2.toObjectId()));
			}
