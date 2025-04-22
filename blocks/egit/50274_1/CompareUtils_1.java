		int exitCode = -1;
		try {
			exitCode = ToolsUtils.executeTool(mergedAbsoluteFilePath,
					localAbsoluteFilePath,
					remoteAbsoluteFilePath, baseAbsoluteFilePath, diffCmd,
					tempDir);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			ToolsUtils.informUserAboutError("difftool - error", //$NON-NLS-1$
					e.getMessage());
		} finally {
			System.out.println("exitCode: " //$NON-NLS-1$
					+ Integer.toString(exitCode));
			if (tempDir != null && !keepTemporaries) {
				ToolsUtils.deleteDirectoryForTempFiles(tempDir);
			}
