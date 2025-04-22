		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			git.commit().setMessage("second commit").setCommitter(committer).call();
			git.commit().setMessage("third commit").setAuthor(author).call();
			git.commit().setMessage("fourth commit").setAuthor(author)
					.setCommitter(committer).call();
			Iterable<RevCommit> commits = git.log().call();

			PersonIdent defaultCommitter = new PersonIdent(db);
			PersonIdent expectedAuthors[] = new PersonIdent[] { defaultCommitter
					committer
			PersonIdent expectedCommitters[] = new PersonIdent[] {
					defaultCommitter
			String expectedMessages[] = new String[] { "initial commit"
					"second commit"
			int l = expectedAuthors.length - 1;
			for (RevCommit c : commits) {
				assertEquals(expectedAuthors[l].getName()
						.getName());
				assertEquals(expectedCommitters[l].getName()
						.getName());
				assertEquals(c.getFullMessage()
				l--;
			}
			assertEquals(l
			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment().startsWith("commit:"));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment().startsWith("commit:"));
