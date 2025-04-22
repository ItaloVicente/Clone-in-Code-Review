			if (isGitRepository(new File(directory, Constants.DOT_GIT), fs))
				return new File(directory, Constants.DOT_GIT);

			final String name = directory.getName();
			final File parent = directory.getParentFile();
			if (isGitRepository(new File(parent, name + Constants.DOT_GIT_EXT), fs))
				return new File(parent, name + Constants.DOT_GIT_EXT);
