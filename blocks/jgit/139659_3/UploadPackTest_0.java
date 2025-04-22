	class RepeatedSubtreeAtSameLevelPreparator {
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
				dcBuilder.addTree(new byte[] {'y'}
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

		uploadV2WithTreeDepthFilter(preparator.commit

		assertTrue(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertEquals(4
	}

	@Test
	public void testV2FetchFilterTreeDepth_repeatTreeAtSameLevelExcludeFile()
			throws Exception {
		RepeatedSubtreeAtSameLevelPreparator preparator =
				new RepeatedSubtreeAtSameLevelPreparator();
		remote.update("master"

		uploadV2WithTreeDepthFilter(preparator.commit

		assertFalse(client.getObjectDatabase()
				.has(preparator.foo.toObjectId()));
		assertEquals(4
	}

