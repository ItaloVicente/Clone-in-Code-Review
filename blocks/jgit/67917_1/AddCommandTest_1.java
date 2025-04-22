	private void createNestedRepo(String path) throws IOException {
		File gitLinkDir = new File(db.getWorkTree()
		FileUtils.mkdir(gitLinkDir);

		FileRepositoryBuilder nestedBuilder = new FileRepositoryBuilder();
		nestedBuilder.setWorkTree(gitLinkDir);

		Repository nestedRepo = nestedBuilder.build();
		nestedRepo.create();

		File readme1 = new File(gitLinkDir
		FileUtils.createNewFile(readme1);
		PrintWriter writer1 = new PrintWriter(readme1);
		writer1.print("content");
		writer1.close();

		File readme2 = new File(gitLinkDir
		FileUtils.createNewFile(readme2);
		PrintWriter writer2 = new PrintWriter(readme2);
		writer2.print("content");
		writer2.close();

		try (Git git = new Git(nestedRepo)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("subrepo commit").call();
		} catch (GitAPIException e) {
			throw new RuntimeException(e);
		}
	}
