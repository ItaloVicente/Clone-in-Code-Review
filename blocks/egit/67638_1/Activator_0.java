			if (mappings.size() > 1) {
				IPath lastPath = null;
				for (RepositoryMapping mapping : mappings) {
					lastPath = mapping.getGitDirAbsolutePath();
				}
				if (lastPath != null) {
					repositoryDir = lastPath.toFile();
				}
			}
