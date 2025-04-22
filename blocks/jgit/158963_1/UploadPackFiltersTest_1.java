	@Test
	public void testFetchWithCombinedFilters() throws Exception {
		InMemoryRepository server2 = newRepo("server2");
		try (TestRepository<InMemoryRepository> remote2 = new TestRepository<>(
				server2)) {

			RevBlob longBlob = remote2.blob("foobar");
			RevBlob shortBlob = remote2.blob("fooba");
			RevBlob deeperBlob = remote2.blob("content-a1");
			RevTree tree = remote2.tree(remote2.file("1"
					remote2.file("2"
					remote2.file("a/1"
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
				tn.setFilterSpec(FilterSpec
						.fromFilterLine("combine:tree:1+blob:limit=5"));
				tn.fetch(NullProgressMonitor.INSTANCE
						Collections.singletonList(new RefSpec(commit.name())));
				assertTrue(client.getObjectDatabase().has(commit.toObjectId()));
				assertTrue(client.getObjectDatabase().has(tree.toObjectId()));
				assertTrue(
						client.getObjectDatabase().has(shortBlob.toObjectId()));
				assertFalse(
						client.getObjectDatabase().has(longBlob.toObjectId()));
				assertFalse(client.getObjectDatabase()
						.has(deeperBlob.toObjectId()));
			}
		}
	}

