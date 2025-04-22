		checkoutBranch("refs/heads/side");
		write(a
		git.add().addFilepattern("a").call();

		String indexState = indexState(MOD_TIME | SMUDGE | LENGTH | CONTENT_ID
				| CONTENT | ASSUME_UNCHANGED);

		MergeResult result = git.merge().include(secondMasterCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();

		assertEquals(MergeStatus.FAILED
		assertEquals(1
		assertEquals(MergeFailureReason.DIRTY_INDEX
				.get("a"));
		assertEquals("a_"
		assertEquals(indexState
				| CONTENT_ID | CONTENT | ASSUME_UNCHANGED));
		assertEquals(null
		assertEquals(RepositoryState.SAFE
