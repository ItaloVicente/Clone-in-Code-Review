
	@Test
	public void testFetchWithBlobMaxBytes() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob longBlob = remote.blob("foobar");
		RevBlob shortBlob = remote.blob("f");
		RevBlob longGitBlob = remote.blob("foobar2");
		RevTree tree = remote.tree(
				remote.file("1"
				remote.file(".gitspecial"
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
			assertTrue(client.hasObject(longGitBlob.toObjectId()));
		}
	}

	@Test
	public void testFetchWithBlobMaxBytes_GitFileAliasedAsNonGit() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server2);
		RevBlob blob1 = remote.blob("appears as .git*");
		RevBlob blob2 = remote.blob("also appears as .git*");
		RevBlob nonGitBlob = remote.blob("never appearing as .git*");

		RevTree tree = remote.tree(
				remote.file(".fitone"
				remote.file(".gitone"
				remote.file(".gittwo"
				remote.file(".hittwo"
				remote.file("nonGitBlob"
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
			tn.setBlobMaxBytes(0);
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertTrue(client.hasObject(blob1.toObjectId()));
			assertTrue(client.hasObject(blob2.toObjectId()));
			assertFalse(client.hasObject(nonGitBlob.toObjectId()));
		}
	}
