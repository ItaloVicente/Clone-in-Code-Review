			Files.copy(Paths.get(mergedFilePath)
					backup.getFile(workingDir
					StandardCopyOption.REPLACE_EXISTING);
			String localFilePath = localFile.getFile(workingDir
					.getPath();
			String remoteFilePath = remoteFile.getFile(workingDir
					.getPath();
			if (baseFile != null) {
				baseFilePath = baseFile.getFile(workingDir
			}
			String command = tool.getCommand(baseFile != null);
