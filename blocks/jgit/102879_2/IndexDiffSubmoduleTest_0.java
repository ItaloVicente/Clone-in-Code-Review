	private void assertDiff(IndexDiff indexDiff
			IgnoreSubmoduleMode... expectedEmptyModes) throws IOException {
		boolean diffResult = indexDiff.diff();
		Set<String> submodulePaths = indexDiff
				.getPathsWithIndexMode(FileMode.GITLINK);
		boolean emptyExpected = false;
		for (IgnoreSubmoduleMode empty : expectedEmptyModes) {
			if (mode.equals(empty)) {
				emptyExpected = true;
				break;
			}
		}
		if (emptyExpected) {
			assertFalse("diff should be false with mode=" + mode
					diffResult);
			assertEquals("should have no paths with FileMode.GIT_LINK"
					submodulePaths.size());
		} else {
			assertTrue("diff should be true with mode=" + mode
					diffResult);
			assertTrue("submodule path should have FileMode.GIT_LINK"
					submodulePaths.contains("modules/submodule"));
		}
	}

