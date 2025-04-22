			RepositoryBuilder builder = new RepositoryBuilder()
					.setGitDir(remoteGitDir);
			if (local != null) {
				builder.setFS(local.getFS());
			}

			return builder.build();
