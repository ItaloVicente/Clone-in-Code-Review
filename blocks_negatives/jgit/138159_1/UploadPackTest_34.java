		TestRepository<InMemoryRepository> remote2 =
				new TestRepository<>(server2);
		RevBlob blob1 = remote2.blob("foobar");
		RevBlob blob2 = remote2.blob("fooba");
		RevTree tree = remote2.tree(remote2.file("1", blob1),
				remote2.file("2", blob2));
		RevCommit commit = remote2.commit(tree);
		remote2.update("master", commit);

		server2.getConfig().setBoolean("uploadpack", null, "allowfilter", true);

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req, Repository db)
							throws ServiceNotEnabledException,
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						return up;
					}
				}, null);
		uri = testProtocol.register(ctx, server2);

		try (Transport tn = testProtocol.open(uri, client, "server2")) {
			tn.setFilterBlobLimit(0);
			tn.fetch(NullProgressMonitor.INSTANCE,
					Collections.singletonList(new RefSpec(commit.name())));
			assertTrue(client.getObjectDatabase().has(tree.toObjectId()));
			assertFalse(client.getObjectDatabase().has(blob1.toObjectId()));
			assertFalse(client.getObjectDatabase().has(blob2.toObjectId()));
