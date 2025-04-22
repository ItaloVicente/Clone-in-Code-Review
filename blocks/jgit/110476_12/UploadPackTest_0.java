
	@Test
	public void testFetchWithBlobNoneFilter() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob blob1 = remote.blob("foobar");
		RevBlob blob2 = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server2.getConfig().setBoolean("uploadpack"

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
			tn.setFilterBlobLimit(0);
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertTrue(client.hasObject(tree.toObjectId()));
			assertFalse(client.hasObject(blob1.toObjectId()));
			assertFalse(client.hasObject(blob2.toObjectId()));
		}
	}

	@Test
	public void testFetchWithBlobLimitFilter() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob longBlob = remote.blob("foobar");
		RevBlob shortBlob = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server2.getConfig().setBoolean("uploadpack"

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
			tn.setFilterBlobLimit(5);
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertFalse(client.hasObject(longBlob.toObjectId()));
			assertTrue(client.hasObject(shortBlob.toObjectId()));
		}
	}

	@Test
	public void testFetchWithBlobLimitFilterAndBitmaps() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob longBlob = remote.blob("foobar");
		RevBlob shortBlob = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server2.getConfig().setBoolean("uploadpack"

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
			tn.setFilterBlobLimit(5);
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertFalse(client.hasObject(longBlob.toObjectId()));
			assertTrue(client.hasObject(shortBlob.toObjectId()));
		}
	}

	@Test
	public void testFetchWithNonSupportingServer() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob blob = remote.blob("foo");
		RevTree tree = remote.tree(remote.file("1"
		RevCommit commit = remote.commit(tree);
		remote.update("master"

		server2.getConfig().setBoolean("uploadpack"

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
			tn.setFilterBlobLimit(0);

			thrown.expect(TransportException.class);
			thrown.expectMessage("filter requires server to advertise that capability");

			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
		}
	}
