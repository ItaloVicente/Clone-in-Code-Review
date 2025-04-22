			writer.append("gitdir: ../" + Constants.DOT_GIT).close();

			FileRepositoryBuilder builder = new FileRepositoryBuilder();
			builder.setWorkTree(dir);
			builder.setMustExist(true);
			Repository repo2 = builder.build();

			assertEquals(repo1.getDirectory().getCanonicalPath(), repo2
					.getDirectory().getCanonicalPath());
			assertEquals(dir, repo2.getWorkTree());
