	@Test
	public void testCherryPickCustomMerger() throws Exception {
		Git git = new Git(db);

		RevCommit sideCommit = prepareCherryPick(git);
		final boolean gotCalled[] = new boolean[1];
		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.mergeWith(new ContentMerger(db) {

					@Override
					public MergeResult<RawText> merge(RawTextComparator cmp
							CanonicalTreeParser base
							CanonicalTreeParser theirs) throws IOException {
						gotCalled[0] = true;
						return null;
					}
				}).call();
		assertTrue(gotCalled[0]);
		assertEquals(CherryPickStatus.CONFLICTING
		assertEquals(RepositoryState.CHERRY_PICKING
		assertTrue(new File(db.getDirectory()
				.exists());

		git.reset().setMode(ResetType.MIXED).setRef("HEAD").call();

		assertEquals(RepositoryState.SAFE
		assertFalse(new File(db.getDirectory()
				.exists());
	}

