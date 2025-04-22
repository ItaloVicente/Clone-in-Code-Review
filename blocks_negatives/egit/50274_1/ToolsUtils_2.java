		try {
			if (reusedTempDir == null) {
				tempDir = createTempDirectory();
			} else {
				tempDir = reusedTempDir;
			}
			File cmdFile = new File(tempDir, "jgit-windows.sh"); //$NON-NLS-1$
			cmdFilePath = cmdFile.getAbsolutePath();
			cmdFilePath = cmdFilePath.replace("\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
			Files.write(cmdFile.toPath(), command.getBytes(),
					StandardOpenOption.CREATE, StandardOpenOption.WRITE,
					StandardOpenOption.TRUNCATE_EXISTING);

		} catch (IOException e) {
			e.printStackTrace();
