		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			RefUpdate r = db.updateRef("refs/heads/side");
			r.setNewObjectId(db.resolve(Constants.HEAD));
			assertEquals(r.forceUpdate()
			RevCommit second = git.commit().setMessage("second commit").setCommitter(committer).call();
			db.updateRef(Constants.HEAD).link("refs/heads/side");
			RevCommit firstSide = git.commit().setMessage("first side commit").setAuthor(author).call();

			write(new File(db.getDirectory()
					.toString(db.resolve("refs/heads/master")));
			write(new File(db.getDirectory()

			RevCommit commit = git.commit().call();
			RevCommit[] parents = commit.getParents();
			assertEquals(parents[0]
			assertEquals(parents[1]
			assertEquals(2
		}
