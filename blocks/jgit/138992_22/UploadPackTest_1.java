	abstract class TreeBuilder {
		abstract void addElements(DirCacheBuilder dcBuilder) throws Exception;

		RevTree build() throws Exception {
			DirCache dc = DirCache.newInCore();
			DirCacheBuilder dcBuilder = dc.builder();
			addElements(dcBuilder);
			dcBuilder.finish();
			ObjectId id;
			try (ObjectInserter ins =
					remote.getRepository().newObjectInserter()) {
				id = dc.writeTree(ins);
				ins.flush();
			}
			return remote.getRevWalk().parseTree(id);
		}
	}

	class DeepTreePreparator {
		RevBlob blobLowDepth = remote.blob("lo");
		RevBlob blobHighDepth = remote.blob("hi");
		RevTree subtree = remote.tree(remote.file("1"
		RevTree rootTree = (new TreeBuilder() {
				@Override
				void addElements(DirCacheBuilder dcBuilder) throws Exception {
					dcBuilder.add(remote.file("1"
					dcBuilder.addTree(new byte[] {'2'}
							remote.getRevWalk().getObjectReader()
				}
			}).build();
		RevCommit commit = remote.commit(rootTree);

		DeepTreePreparator() throws Exception {}
	}

	private void uploadV2WithTreeDepthFilter(RevCommit commit
			throws Exception {
		server.getConfig().setBoolean("uploadpack"

		ByteArrayInputStream recvStream = uploadPackV2(
			"command=fetch\n"
			PacketLineIn.DELIM
			"want " + commit.toObjectId().getName() + "\n"
			"filter tree:" + depth + "\n"
			"done\n"
			PacketLineIn.END);
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString()
		parsePack(recvStream);
	}

	@Test
	public void testV2FetchFilterTreeDepth0() throws Exception {
		DeepTreePreparator preparator = new DeepTreePreparator();
		remote.update("master"

		uploadV2WithTreeDepthFilter(preparator.commit

		assertFalse(client.getObjectDatabase()
				.has(preparator.rootTree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.subtree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobLowDepth.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobHighDepth.toObjectId()));
	}

	@Test
	public void testV2FetchFilterTreeDepth1_serverHasBitmap() throws Exception {
		DeepTreePreparator preparator = new DeepTreePreparator();
		remote.update("master"

		generateBitmaps(server);

		uploadV2WithTreeDepthFilter(preparator.commit

		assertTrue(client.getObjectDatabase()
				.has(preparator.rootTree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.subtree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobLowDepth.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobHighDepth.toObjectId()));
	}

	@Test
	public void testV2FetchFilterTreeDepth2() throws Exception {
		DeepTreePreparator preparator = new DeepTreePreparator();
		remote.update("master"

		uploadV2WithTreeDepthFilter(preparator.commit

		assertTrue(client.getObjectDatabase()
				.has(preparator.rootTree.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.subtree.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.blobLowDepth.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobHighDepth.toObjectId()));
	}

	class RepeatedSubtreePreparator {
		RevBlob foo = remote.blob("foo");
		RevTree subtree3 = remote.tree(remote.file("foo"
		RevTree subtree2 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'b'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();
		RevTree subtree1 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'x'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();
		RevTree rootTree = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'a'}
						remote.getRevWalk().getObjectReader()
				dcBuilder.addTree(new byte[] {'x'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();
		RevCommit commit = remote.commit(rootTree);

		RepeatedSubtreePreparator() throws Exception {}
	}

	@Test
	public void testV2FetchFilterTreeDepth_iterateOverTreeAtTwoLevels()
			throws Exception {
		RepeatedSubtreePreparator preparator = new RepeatedSubtreePreparator();
		remote.update("master"

		uploadV2WithTreeDepthFilter(preparator.commit

		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
	}

