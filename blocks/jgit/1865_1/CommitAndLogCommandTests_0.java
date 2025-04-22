
	public void testCommitRange() throws NoHeadException
			UnmergedPathException
			JGitInternalException
			IncorrectObjectTypeException
		Git git = new Git(db);
		git.commit().setMessage("first commit").call();
		RevCommit second = git.commit().setMessage("second commit")
				.setCommitter(committer).call();
		git.commit().setMessage("third commit").setAuthor(author).call();
		RevCommit last = git.commit().setMessage("fourth commit").setAuthor(
				author)
				.setCommitter(committer).call();
		Iterable<RevCommit> commits = git.log().addRange(second.getId()
				last.getId()).call();

		PersonIdent defaultCommitter = new PersonIdent(db);
		PersonIdent expectedAuthors[] = new PersonIdent[] { author
		PersonIdent expectedCommitters[] = new PersonIdent[] {
				defaultCommitter
		String expectedMessages[] = new String[] { "third commit"
				"fourth commit" };
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
	}
