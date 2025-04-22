				for (RepositoryMapping m : mappings) {
					IPath gitDir = m.getGitDirAbsolutePath();
					if (gitDir != null
							&& repositoryDir.equals(gitDir.toFile())) {
						gitDir = mappings.iterator().next()
								.getGitDirAbsolutePath();
						if (gitDir != null) {
							connections.put(project, gitDir.toFile());
						}
					}
				}
