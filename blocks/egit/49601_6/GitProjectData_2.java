		IPath absolutePath = m.getGitDirAbsolutePath();
		if (absolutePath == null) {
			logAndUnmapGoneMappedResource(m);
			return;
		}
		git = absolutePath.toFile();
