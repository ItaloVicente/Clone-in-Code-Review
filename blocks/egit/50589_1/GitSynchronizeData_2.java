		if (rm != null) {
			for (IResource resource : includedResources) {
				String repoRelativePath = rm.getRepoRelativePath(resource);
				if (repoRelativePath != null && repoRelativePath.length() > 0)
					paths.add(repoRelativePath);
			}
