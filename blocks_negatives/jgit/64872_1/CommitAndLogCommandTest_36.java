		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		RefUpdate r = db.updateRef("refs/heads/side");
		r.setNewObjectId(db.resolve(Constants.HEAD));
		assertEquals(r.forceUpdate(), RefUpdate.Result.NEW);
		RevCommit second = git.commit().setMessage("second commit").setCommitter(committer).call();
		db.updateRef(Constants.HEAD).link("refs/heads/side");
		RevCommit firstSide = git.commit().setMessage("first side commit").setAuthor(author).call();

		write(new File(db.getDirectory(), Constants.MERGE_HEAD), ObjectId
				.toString(db.resolve("refs/heads/master")));
		write(new File(db.getDirectory(), Constants.MERGE_MSG), "merging");

		RevCommit commit = git.commit().call();
		RevCommit[] parents = commit.getParents();
		assertEquals(parents[0], firstSide);
		assertEquals(parents[1], second);
		assertEquals(2, parents.length);
