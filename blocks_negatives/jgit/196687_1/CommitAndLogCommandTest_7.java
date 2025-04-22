			PersonIdent expectedAuthors[] = new PersonIdent[] {
					defaultCommitter, committer, author, author };
			PersonIdent expectedCommitters[] = new PersonIdent[] {
					defaultCommitter, committer, defaultCommitter, committer };
			String expectedMessages[] = new String[] { "initial commit",
					"second commit", "third commit", "fourth commit" };
