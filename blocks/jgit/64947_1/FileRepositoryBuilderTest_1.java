		try (FileWriter writer = new FileWriter(dotGit)) {
			writer.append(
					"gitdir: " + repo1.getDirectory().getAbsolutePath()).close();
			FileRepositoryBuilder builder = new FileRepositoryBuilder();

			builder.setWorkTree(dir);
			builder.findGitDir(dir);
			assertEquals(repo1.getDirectory().getAbsolutePath()
					.getGitDir().getAbsolutePath());
			builder.setMustExist(true);
			Repository repo2 = builder.build();

			assertEquals(repo1.getDirectory().getCanonicalPath()
					.getDirectory().getCanonicalPath());
			assertEquals(dir
		}
