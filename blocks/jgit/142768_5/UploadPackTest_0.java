	@Test
	public void testFetchReachableBlobWithoutBitmapButFilterAllowed() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob = remote2.blob("foo");
			RevCommit commit = remote2.commit(remote2.tree(remote2.file("foo"
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					true);

			testProtocol = new TestProtocol<>((Object req
				UploadPack up = new UploadPack(db);
				up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
				return up;
			}
			uri = testProtocol.register(ctx

			assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

			try (Transport tn = testProtocol.open(uri
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(blob.name())));
				assertTrue(client.getObjectDatabase().has(blob.toObjectId()));
			}
		}
	}

