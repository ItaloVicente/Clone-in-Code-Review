
	private void generateBitmaps(InMemoryRepository repo) throws Exception {
		new DfsGarbageCollector(repo).pack(null);
		repo.scanForRepoChanges();
	}

	@Test
	public void testFetchUnreachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		generateBitmaps(server);

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
						return up;
					}
				}
		uri = testProtocol.register(ctx

		assertFalse(client.hasObject(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
			fail("expected TransportException");
		} catch (TransportException exception) {
			assertTrue(exception.getMessage().contains("want " + blob.name() + " not valid"));
		}
	}

	@Test
	public void testFetchReachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"
		generateBitmaps(server);

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
						return up;
					}
				}
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

		testProtocol = new TestProtocol<>(
				new UploadPackFactory<Object>() {
					@Override
					public UploadPack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						UploadPack up = new UploadPack(db);
						up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
						return up;
					}
				}
		uri = testProtocol.register(ctx

		assertFalse(client.hasObject(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
			fail("expected TransportException");
		} catch (TransportException exception) {
			assertTrue(exception.getMessage().contains("want " + blob.name() + " not valid"));
		}
	}
