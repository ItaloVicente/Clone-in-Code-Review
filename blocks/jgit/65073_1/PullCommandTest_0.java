		try (RevWalk rw = new RevWalk(dbTarget)) {
			RevCommit mergeCommit = rw.parseCommit(mergeResult.getNewHead());
			String message = "Merge branch 'master' of "
					+ db.getWorkTree().getAbsolutePath();
			assertEquals(message
		}
