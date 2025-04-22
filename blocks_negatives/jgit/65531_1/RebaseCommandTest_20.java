		RevWalk walk = new RevWalk(db);
		ObjectId headId = db.resolve(Constants.HEAD);
		RevCommit headCommit = walk.parseCommit(headId);
		assertEquals(headCommit.getFullMessage(),
				"update file2 on master\nnew line");

		ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
		RevCommit head1Commit = walk.parseCommit(head2Id);
		assertEquals(
				"Add file1\nnew line\nAdd file2\nnew line\nupdated file1 on master\nnew line",
				head1Commit.getFullMessage());
