	class RepeatedSubtreeAtSameLevelPreparator {
		RevBlob foo = remote.blob("foo");

		RevTree subtree1 = remote.tree(remote.file("foo"

		RevTree subtree2 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'b'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();

		RevTree subtree3 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'x'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();

		RevBlob baz = remote.blob("baz");

		RevTree subtree4 = remote.tree(remote.file("baz"

		RevTree subtree5 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'c'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();

		RevTree subtree6 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'u'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();

		RevTree subtree7 = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'v'}
						remote.getRevWalk().getObjectReader()
			}
		}).build();

		RevTree rootTree = (new TreeBuilder() {
			@Override
			void addElements(DirCacheBuilder dcBuilder) throws Exception {
				dcBuilder.addTree(new byte[] {'a'}
						remote.getRevWalk().getObjectReader()
				dcBuilder.addTree(new byte[] {'b'}
						remote.getRevWalk().getObjectReader()
				dcBuilder.addTree(new byte[] {'y'}
						remote.getRevWalk().getObjectReader()
				dcBuilder.addTree(new byte[] {'z'}
						remote.getRevWalk().getObjectReader()
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
		remote.update("master"

		uploadV2WithTreeDepthFilter(5

		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertTrue(client.getObjectDatabase()
				.has(preparator.baz.toObjectId()));
		assertEquals(8
	}

	@Test
	public void testV2FetchFilterTreeDepth_repeatTreeAtSameLevelExcludeFile()
			throws Exception {
		RepeatedSubtreeAtSameLevelPreparator preparator =
				new RepeatedSubtreeAtSameLevelPreparator();
		remote.update("master"

		uploadV2WithTreeDepthFilter(4

		assertFalse(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertFalse(client.getObjectDatabase()
				.has(preparator.baz.toObjectId()));
		assertEquals(8
	}

