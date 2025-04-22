	@Test
	public void testFetchUnreachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		remote.commit(remote.tree(remote.file("foo", blob)));
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx, server);

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri, client, "server")) {
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE,
					Collections.singletonList(new RefSpec(blob.name())));
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
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE,
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

