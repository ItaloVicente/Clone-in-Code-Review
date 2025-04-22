		Git git = new Git(repository);
		CommitCommand commitCommand = git.commit();
		commitCommand.setAuthor("J. Git", "j.git@egit.org");
		commitCommand.setCommitter(commitCommand.getAuthor());
		commitCommand.setMessage(message);
		return commitCommand.call();
