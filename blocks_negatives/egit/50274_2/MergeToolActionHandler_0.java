					int exitCode = ToolsUtils.executeTool(mergedCompareFilePath,
							localCompareFilePath,
							remoteCompareFilePath, baseCompareFilePath,
							mergeCmd, tempDir);
					if (tempDir != null && !keepTemporaries) {
						ToolsUtils.deleteDirectoryForTempFiles(tempDir);
