		try (FileInputStream fis = new FileInputStream(
				new File(db.getWorkTree()
			MergeResult mergeRes = git.merge().setStrategy(strategy)
					.include(masterCommit).call();
			if (mergeRes.getMergeStatus().equals(MergeStatus.FAILED)) {
				assertEquals(1
				assertEquals(MergeFailureReason.COULD_NOT_DELETE
						mergeRes.getFailingPaths().get("b.txt"));
			}
			assertEquals(
					"[a.txt
							+ "[c.txt
					indexState(CONTENT));
