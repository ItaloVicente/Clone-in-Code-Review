		FileInputStream fis = new FileInputStream(new File(db.getWorkTree(),
				"b.txt"));
		MergeResult mergeRes = git.merge().setStrategy(strategy)
				.include(masterCommit).call();
		if (mergeRes.getMergeStatus().equals(MergeStatus.FAILED)) {
			assertEquals(1, mergeRes.getFailingPaths().size());
			assertEquals(MergeFailureReason.COULD_NOT_DELETE, mergeRes
					.getFailingPaths().get("b.txt"));
