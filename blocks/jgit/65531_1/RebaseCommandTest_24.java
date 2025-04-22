		try (RevWalk walk = new RevWalk(db)) {
			ObjectId headId = db.resolve(Constants.HEAD);
			RevCommit headCommit = walk.parseCommit(headId);
			assertEquals("Add file2"
					headCommit.getFullMessage());
		}
