		TestRepository<InMemoryRepository> remote2 =
				new TestRepository<>(server2);
		RevBlob longBlob = remote2.blob("foobar");
		RevBlob shortBlob = remote2.blob("fooba");
		RevTree tree = remote2.tree(remote2.file("1", longBlob),
				remote2.file("2", shortBlob));
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
			tn.setFilterBlobLimit(5);
			tn.fetch(NullProgressMonitor.INSTANCE,
					Collections.singletonList(new RefSpec(commit.name())));
			assertFalse(client.hasObject(longBlob.toObjectId()));
			assertTrue(client.hasObject(shortBlob.toObjectId()));
