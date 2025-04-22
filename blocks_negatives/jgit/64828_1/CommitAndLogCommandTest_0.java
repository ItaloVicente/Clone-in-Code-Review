		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.commit().setMessage("second commit").setCommitter(committer).call();
		git.commit().setMessage("third commit").setAuthor(author).call();
		git.commit().setMessage("fourth commit").setAuthor(author)
				.setCommitter(committer).call();
		Iterable<RevCommit> commits = git.log().call();

		PersonIdent defaultCommitter = new PersonIdent(db);
		PersonIdent expectedAuthors[] = new PersonIdent[] { defaultCommitter,
				committer, author, author };
		PersonIdent expectedCommitters[] = new PersonIdent[] {
				defaultCommitter, committer, defaultCommitter, committer };
		String expectedMessages[] = new String[] { "initial commit",
				"second commit", "third commit", "fourth commit" };
		int l = expectedAuthors.length - 1;
		for (RevCommit c : commits) {
			assertEquals(expectedAuthors[l].getName(), c.getAuthorIdent()
					.getName());
			assertEquals(expectedCommitters[l].getName(), c.getCommitterIdent()
					.getName());
			assertEquals(c.getFullMessage(), expectedMessages[l]);
			l--;
