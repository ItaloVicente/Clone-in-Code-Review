	private void setWorkTree(File gitDir, File workTree) throws IOException {
		Repository repo = new Repository(gitDir, null);
		repo.getConfig()
				.setString(ConfigConstants.CONFIG_CORE_SECTION, null,
						ConfigConstants.CONFIG_KEY_WORKTREE,
						workTree.getAbsolutePath());
		repo.getConfig().save();
