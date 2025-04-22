			final RepositoryBuilder builder = new RepositoryBuilder();
			builder.setGitDir(remoteGitDir);
			if (local != null) {
				builder.setFS(local.getFS());
			}

			return builder.build();
