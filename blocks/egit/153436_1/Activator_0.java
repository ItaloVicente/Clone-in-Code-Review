				Set<String> configured = Activator.getDefault()
						.getRepositoryUtil().getRepositories();
				if (!configured.contains(gitDirPath.toString())) {
					IPath lastPath = gitDirPath;
					for (int i = 1; i < nofMappings; i++) {
						IPath nextPath = mappings.get(i)
								.getGitDirAbsolutePath();
						if (nextPath == null) {
							continue;
						}
						if (configured.contains(nextPath.toString())) {
							return;
						} else if (!isValidRepositoryPath(nextPath)) {
							break;
						}
						lastPath = nextPath;
					}
