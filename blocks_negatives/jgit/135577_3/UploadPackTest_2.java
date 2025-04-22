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

		RevTree subtree = remote.tree(remote.file("1", blobHighDepth));
		RevTree rootTree = (new TreeBuilder() {
				@Override
				void addElements(DirCacheBuilder dcBuilder) throws Exception {
					dcBuilder.add(remote.file("1", blobLowDepth));
					dcBuilder.addTree(new byte[] {'2'}, DirCacheEntry.STAGE_0,
							remote.getRevWalk().getObjectReader(), subtree);
				}
			}).build();
		RevCommit commit = remote.commit(rootTree);

		DeepTreePreparator() throws Exception {}
	}

	private void uploadV2WithTreeDepthFilter(
			long depth, ObjectId... wants) throws Exception {
		server.getConfig().setBoolean("uploadpack", null, "allowfilter", true);

		List<String> input = new ArrayList<>();
		input.add("command=fetch\n");
		input.add(PacketLineIn.delimiter());
		for (ObjectId want : wants) {
			input.add("want " + want.getName() + "\n");
		}
		input.add("filter tree:" + depth + "\n");
		input.add("done\n");
		input.add(PacketLineIn.end());
		ByteArrayInputStream recvStream =
				uploadPackV2(RequestPolicy.ANY, /*refFilter=*/null,
							 /*hook=*/null, input.toArray(new String[0]));
		PacketLineIn pckIn = new PacketLineIn(recvStream);
		assertThat(pckIn.readString(), is("packfile"));
		parsePack(recvStream);
	}

	@Test
	public void testV2FetchFilterTreeDepth0() throws Exception {
		DeepTreePreparator preparator = new DeepTreePreparator();
		remote.update("master", preparator.commit);

		uploadV2WithTreeDepthFilter(0, preparator.commit.toObjectId());

		assertFalse(client.getObjectDatabase()
				.has(preparator.rootTree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.subtree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobLowDepth.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobHighDepth.toObjectId()));
		assertEquals(1, stats.getTreesTraversed());
	}

	@Test
	public void testV2FetchFilterTreeDepth1_serverHasBitmap() throws Exception {
		DeepTreePreparator preparator = new DeepTreePreparator();
		remote.update("master", preparator.commit);

		generateBitmaps(server);

		uploadV2WithTreeDepthFilter(1, preparator.commit.toObjectId());

		assertTrue(client.getObjectDatabase()
				.has(preparator.rootTree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.subtree.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobLowDepth.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobHighDepth.toObjectId()));
		assertEquals(1, stats.getTreesTraversed());
	}

	@Test
	public void testV2FetchFilterTreeDepth2() throws Exception {
		DeepTreePreparator preparator = new DeepTreePreparator();
		remote.update("master", preparator.commit);

		uploadV2WithTreeDepthFilter(2, preparator.commit.toObjectId());

		assertTrue(client.getObjectDatabase()
				.has(preparator.rootTree.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.subtree.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.blobLowDepth.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.blobHighDepth.toObjectId()));
		assertEquals(2, stats.getTreesTraversed());
	}

	/**
	 * Creates a commit with the following files:
	 * <pre>
	 * a/x/b/foo
	 * x/b/foo
	 * </pre>
	 * which has an identical tree in two locations: once at / and once at /a
	 */
	class RepeatedSubtreePreparator {
		RevBlob foo = remote.blob("foo");
		RevTree subtree3 = remote.tree(remote.file("foo", foo));
		RevTree subtree2 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'b'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree3);
			}
		}).build();
		RevTree subtree1 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'x'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree2);
			}
		}).build();
		RevTree rootTree = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'a'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree1);
				dcBuilder.addTree(new byte[] {'x'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree2);
			}
		}).build();
		RevCommit commit = remote.commit(rootTree);

		RepeatedSubtreePreparator() throws Exception {}
	}

	@Test
	public void testV2FetchFilterTreeDepth_iterateOverTreeAtTwoLevels()
			throws Exception {
		RepeatedSubtreePreparator preparator = new RepeatedSubtreePreparator();
		remote.update("master", preparator.commit);

		uploadV2WithTreeDepthFilter(4, preparator.commit.toObjectId());

		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
	}

	/**
	 * Creates a commit with the following files:
	 * <pre>
	 * a/x/b/foo
	 * b/u/c/baz
	 * y/x/b/foo
	 * z/v/c/baz
	 * </pre>
	 * which has two pairs of identical trees:
	 * <ul>
	 * <li>one at /a and /y
	 * <li>one at /b/u and /z/v
	 * </ul>
	 * Note that this class defines unique 8 trees (rootTree and subtree1-7)
	 * which means PackStatistics should report having visited 8 trees.
	 */
	class RepeatedSubtreeAtSameLevelPreparator {
		RevBlob foo = remote.blob("foo");

		/** foo */
		RevTree subtree1 = remote.tree(remote.file("foo", foo));

		/** b/foo */
		RevTree subtree2 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'b'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree1);
			}
		}).build();

		/** x/b/foo */
		RevTree subtree3 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'x'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree2);
			}
		}).build();

		RevBlob baz = remote.blob("baz");

		/** baz */
		RevTree subtree4 = remote.tree(remote.file("baz", baz));

		/** c/baz */
		RevTree subtree5 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'c'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree4);
			}
		}).build();

		/** u/c/baz */
		RevTree subtree6 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'u'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree5);
			}
		}).build();

		/** v/c/baz */
		RevTree subtree7 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'v'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree5);
			}
		}).build();

		RevTree rootTree = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'a'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree3);
				dcBuilder.addTree(new byte[] {'b'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree6);
				dcBuilder.addTree(new byte[] {'y'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree3);
				dcBuilder.addTree(new byte[] {'z'}, DirCacheEntry.STAGE_0,
						remote.getRevWalk().getObjectReader(), subtree7);
			}
		}).build();
		RevCommit commit = remote.commit(rootTree);

		RepeatedSubtreeAtSameLevelPreparator() throws Exception {}
	}

	@Test
	public void testV2FetchFilterTreeDepth_repeatTreeAtSameLevelIncludeFile()
			throws Exception {
		RepeatedSubtreeAtSameLevelPreparator preparator =
				new RepeatedSubtreeAtSameLevelPreparator();
		remote.update("master", preparator.commit);

		uploadV2WithTreeDepthFilter(5, preparator.commit.toObjectId());

		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.baz.toObjectId()));
		assertEquals(8, stats.getTreesTraversed());
	}

	@Test
	public void testV2FetchFilterTreeDepth_repeatTreeAtSameLevelExcludeFile()
