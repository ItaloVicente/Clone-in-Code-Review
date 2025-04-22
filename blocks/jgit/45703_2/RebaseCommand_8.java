			try (Git git = new Git(repo)) {
				CommitCommand commit = git.commit();
				commit.setMessage(rebaseState.readFile(MESSAGE));
				commit.setAuthor(parseAuthor());
				return commit.call();
			}
