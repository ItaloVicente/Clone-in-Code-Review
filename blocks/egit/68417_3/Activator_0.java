			int nofMappings = mappings.size();
			if (nofMappings > 1) {
				IPath lastPath = mappings.get(nofMappings - 1)
						.getGitDirAbsolutePath();
				if (lastPath != null) {
					repositoryDir = lastPath.toFile();
				}
			}
