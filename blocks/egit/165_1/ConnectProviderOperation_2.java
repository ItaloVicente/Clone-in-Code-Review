			String mappingDir = rm.getGitDir();
			if (!rm.getContainerPath().isEmpty()) {
				mappingDir = rm.getContainerPath() + "/" + mappingDir;
			}
			if (File.separatorChar != '/') {
				mappingDir = mappingDir.replace('/', File.separatorChar);
			}
			String suggestedDir = suggestedRepo.getPath();
			if (mappingDir.equals(suggestedDir))
