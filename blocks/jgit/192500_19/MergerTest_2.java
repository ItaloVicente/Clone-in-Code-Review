		MergeResult mergeResult = git.merge().include(renameCommit).setStrategy(strategy)
				.setFindRenames(enableRenameDetection).call();

		if (!enableRenameDetection) {
			assertEquals(mergeResult.getMergeStatus()
			assertEquals(slightlyModifiedContent
			assertEquals(originalContent

			assertEquals(
					"[test/file1
					indexState(CONTENT));
		} else {
			assertEquals(mergeResult.getMergeStatus()
			assertFalse(check("test/file1"));
			assertEquals(slightlyModifiedContent
		}
