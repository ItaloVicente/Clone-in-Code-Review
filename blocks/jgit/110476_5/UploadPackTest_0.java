
	@Test
	public void testFetchWithBlobMaxBytes() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob longBlob = remote.blob("foobar");
		RevBlob shortBlob = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
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
			tn.setBlobMaxBytes(5);
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertFalse(client.hasObject(longBlob.toObjectId()));
			assertTrue(client.hasObject(shortBlob.toObjectId()));
		}
	}

	@Test
	public void testFetchWithBlobMaxBytesBitmaps() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob longBlob = remote.blob("foobar");
		RevBlob shortBlob = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		new DfsGarbageCollector(server2).pack(null);
		server2.scanForRepoChanges();

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
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
			tn.setBlobMaxBytes(5);
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertFalse(client.hasObject(longBlob.toObjectId()));
			assertTrue(client.hasObject(shortBlob.toObjectId()));
		}
	}
