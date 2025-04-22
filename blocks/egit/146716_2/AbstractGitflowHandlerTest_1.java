	protected Git setContentAndStage(String newContent)
			throws Exception, GitAPIException, NoFilepatternException {
		setTestFileContent(newContent);

		Git git = Git.wrap(repository);
		git.add().addFilepattern(".").call();
		return git;
	}

