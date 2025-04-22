			backup = createBackupFile(mergedFilePath, tempDir);
			String localFilePath = localFile.getFile(tempDir, "LOCAL") //$NON-NLS-1$
					.getPath();
			String remoteFilePath = remoteFile.getFile(tempDir, "REMOTE") //$NON-NLS-1$
					.getPath();
			if (baseFile != null) {
				baseFilePath = baseFile.getFile(tempDir, "BASE").getPath(); //$NON-NLS-1$
			}
