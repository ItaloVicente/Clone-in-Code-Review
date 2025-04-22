		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals("update file2 on master\nnew line",
				headCommit.getFullMessage());

		ObjectId head1Id = db.resolve(Constants.HEAD + "^1");
		RevCommit head1Commit = walk.parseCommit(head1Id);
		assertEquals("Add file2\nnew line",
				head1Commit.getFullMessage());
