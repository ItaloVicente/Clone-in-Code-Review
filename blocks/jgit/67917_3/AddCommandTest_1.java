	private void createNestedRepo(String path) throws IOException {
		File gitLinkDir = new File(db.getWorkTree()
		FileUtils.mkdir(gitLinkDir);

		FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
		nestedBuilder.setWorkTree(gitLinkDir);

		Repository nestedRepo = nestedBuilder.build();
		nestedRepo.create();

		writeTrashFile(path
		writeTrashFile(path

		try (Git git = new Git(nestedRepo)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("subrepo commit").call();
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
