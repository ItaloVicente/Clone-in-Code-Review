		} else if (leftRevision != null) {
			mergedFileName = leftRevision.getName();
			String leftFilePath = leftRevision.getPath();
			if (leftFilePath != null) {
				IPath leftFile = ResourceUtil
						.getFileForLocation(repository, leftFilePath)
						.getRawLocation();
				mergedAbsoluteFilePath = leftFile.toOSString();
				baseDir = leftFile.toFile().getParentFile();
			}
