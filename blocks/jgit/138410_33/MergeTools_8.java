			tempDir = config.isWriteToTemp()
					: workingDir;
			backup = createBackupFile(mergedFilePath
			String localFilePath = localFile.getFile(tempDir
					.getPath();
			String remoteFilePath = remoteFile.getFile(tempDir
					.getPath();
			if (baseFile != null) {
				baseFilePath = baseFile.getFile(tempDir
			}
