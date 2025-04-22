				if (!mappings.isEmpty()) {
					IPath gitDir = mappings.iterator().next()
							.getGitDirAbsolutePath();
					if (gitDir != null) {
						connections.put(project, gitDir.toFile());
					}
				}
