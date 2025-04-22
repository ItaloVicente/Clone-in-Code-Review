		try (RevWalk rw = new RevWalk(dbTarget)) {
			RevCommit mergeCommit = rw.parseCommit(mergeResult.getNewHead());
			String message = "Merge branch 'other' of "
					+ db.getWorkTree().getAbsolutePath() + " into other";
			assertEquals(message
		}
