				IFile leftFile = ResourceUtil.getFileForLocation(repository,
						leftFilePath);
				if (leftFile != null) {
					IPath leftPath = leftFile.getRawLocation();
					mergedAbsoluteFilePath = leftPath.toOSString();
					mergedDirPath = leftPath.toFile().getParentFile();
				}
