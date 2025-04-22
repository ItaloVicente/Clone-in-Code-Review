		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob longBlob = remote2.blob("foobar");
			RevBlob shortBlob = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					true);

			new DfsGarbageCollector(server2).pack(null);
			server2.scanForRepoChanges();

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
				tn.setFilterBlobLimit(5);
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
				assertFalse(
						client.getObjectDatabase().has(longBlob.toObjectId()));
				assertTrue(
						client.getObjectDatabase().has(shortBlob.toObjectId()));
			}
