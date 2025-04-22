
	public void testMergeEmptyBranches() throws IOException
			NoMessageException
			JGitInternalException
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		RefUpdate r = db.updateRef("refs/heads/side");
		r.setNewObjectId(db.resolve(Constants.HEAD));
		assertEquals(r.forceUpdate()
		RevCommit second = git.commit().setMessage("second commit").setCommitter(committer).call();
		db.updateRef(Constants.HEAD).link("refs/heads/side");
		RevCommit firstSide = git.commit().setMessage("first side commit").setAuthor(author).call();

		FileWriter wr = new FileWriter(new File(db.getDirectory()
				Constants.MERGE_HEAD));
		wr.write(ObjectId.toString(db.resolve("refs/heads/master")));
		wr.close();
		wr = new FileWriter(new File(db.getDirectory()
		wr.write("merging");
		wr.close();

		RevCommit commit = git.commit().call();
		RevCommit[] parents = commit.getParents();
		assertEquals(parents[0]
		assertEquals(parents[1]
		assertTrue(parents.length==2);
	}
