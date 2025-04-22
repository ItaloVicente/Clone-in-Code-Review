	@Test
	public void testSafeToClearRefsInFetchV0() throws Exception {
		server =
			new RefCallsCountingRepository(
				new DfsRepositoryDescription("server"));
		remote = new TestRepository<>(server);
		RevCommit one = remote.commit().message("1").create();
		remote.update("one"
		testProtocol = new TestProtocol<>((Object req
			UploadPack up = new UploadPack(db);
			return up;
		}
		uri = testProtocol.register(ctx
		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
				Collections.singletonList(new RefSpec(one.name())));
		}
		assertTrue(client.getObjectDatabase().has(one.toObjectId()));
		assertEquals(1
	}

	@Test
	public void testSafeToClearRefsInFetchV2() throws Exception {
		server =
			new RefCallsCountingRepository(
				new DfsRepositoryDescription("server"));
		remote = new TestRepository<>(server);
		RevCommit one = remote.commit().message("1").create();
		RevCommit two = remote.commit().message("2").create();
		remote.update("one"
		remote.update("two"
		server.getConfig().setBoolean("uploadpack"
		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.delimiter()
			"want-ref refs/heads/one\n"
			"want-ref refs/heads/two\n"
			"done\n"
			PacketLineIn.end());
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		assertThat(
			Arrays.asList(pckIn.readString()
			hasItems(
				one.toObjectId().getName() + " refs/heads/one"
				two.toObjectId().getName() + " refs/heads/two"));
		assertTrue(PacketLineIn.isDelimiter(pckIn.readString()));
		assertThat(pckIn.readString()
		parsePack(recvStream);
		assertTrue(client.getObjectDatabase().has(one.toObjectId()));
		assertEquals(1
	}

	private class RefCallsCountingRepository extends InMemoryRepository {
		private final InMemoryRepository.MemRefDatabase refdb;
		private int numRefCalls;

		public RefCallsCountingRepository(DfsRepositoryDescription repoDesc) {
			super(repoDesc);
			refdb = new InMemoryRepository.MemRefDatabase() {
				@Override
				public List<Ref> getRefs() throws IOException {
					numRefCalls++;
					return super.getRefs();
				}
			};
		}

		public int numRefCalls() {
			return numRefCalls;
		}

		@Override
		public RefDatabase getRefDatabase() {
			return refdb;
		}
	}
