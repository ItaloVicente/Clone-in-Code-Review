
	@Test
	public void testFetchUnreachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.hasObject(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

	@Test
	public void testFetchReachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.hasObject(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
			assertTrue(client.hasObject(blob.toObjectId()));
		}
	}

	@Test
	public void testFetchReachableBlobWithoutBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.hasObject(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}
