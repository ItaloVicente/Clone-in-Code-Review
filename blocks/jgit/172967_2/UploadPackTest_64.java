	@Test
	public void testFetchWithTreeZeroFilter() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {
			RevBlob blob1 = remote2.blob("foobar");
			RevBlob blob2 = remote2.blob("fooba");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
			RevCommit commit = remote2.commit(tree);
			remote2.update("master"

			server2.getConfig().setBoolean("uploadpack"
					true);

			testProtocol = new TestProtocol<>((Object req
				UploadPack up = new UploadPack(db);
				return up;
			}
			uri = testProtocol.register(ctx

			try (Transport tn = testProtocol.open(uri
				tn.setFilterSpec(FilterSpec.withTreeDepthLimit(0));
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
				assertFalse(client.getObjectDatabase().has(tree.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob1.toObjectId()));
				assertFalse(client.getObjectDatabase().has(blob2.toObjectId()));
			}
		}
	}

