				if (!mappings.isEmpty()) {
					IPath gitDir = mappings.get(0).getGitDirAbsolutePath();
					if (gitDir != null) {
						connections.put(project, gitDir.toFile());
					}
				}
