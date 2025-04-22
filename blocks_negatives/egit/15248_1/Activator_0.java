			try {
				if (mappings.size() == 1) {
					RepositoryMapping m = mappings.iterator()
							.next();
					final File repositoryDir = m
							.getGitDirAbsolutePath().toFile();
