	@Test
	public void testFetchUnreachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		remote.commit(remote.tree(remote.file("foo", blob)));
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx, server);

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri, client, "server")) {
			TransportException e = assertThrows(TransportException.class,
					() -> tn.fetch(NullProgressMonitor.INSTANCE, Collections
							.singletonList(new RefSpec(blob.name()))));
			assertThat(e.getMessage(),
					containsString("want " + blob.name() + " not valid"));
		}
	}

	@Test
	public void testFetchReachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo", blob)));
		remote.update("master", commit);
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx, server);

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri, client, "server")) {
			tn.fetch(NullProgressMonitor.INSTANCE,
					Collections.singletonList(new RefSpec(blob.name())));
			assertTrue(client.getObjectDatabase().has(blob.toObjectId()));
		}
	}

	@Test
	public void testFetchReachableBlobWithoutBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo", blob)));
		remote.update("master", commit);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx, server);

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri, client, "server")) {
			TransportException e = assertThrows(TransportException.class,
					() -> tn.fetch(NullProgressMonitor.INSTANCE, Collections
							.singletonList(new RefSpec(blob.name()))));
			assertThat(e.getMessage(),
					containsString(
						"want " + blob.name() + " not valid"));
		}
	}

	@Test
	public void testFetchReachableBlobWithoutBitmapButFilterAllowed() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob = remote2.blob("foo");
			RevCommit commit = remote2.commit(remote2.tree(remote2.file("foo", blob)));
			remote2.update("master", commit);

			server2.getConfig().setBoolean("uploadpack", null, "allowfilter",
					true);

			testProtocol = new TestProtocol<>((Object req, Repository db) -> {
				UploadPack up = new UploadPack(db);
				up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
				return up;
			}, null);
			uri = testProtocol.register(ctx, server2);

			assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

			try (Transport tn = testProtocol.open(uri, client, "server2")) {
				tn.fetch(NullProgressMonitor.INSTANCE,
						Collections.singletonList(new RefSpec(blob.name())));
				assertTrue(client.getObjectDatabase().has(blob.toObjectId()));
			}
		}
	}

