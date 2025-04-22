					int exitCode = -1;
					try {
						exitCode = ToolsUtils.executeTool(mergedAbsoluteFilePath,
								localAbsoluteFilePath, remoteAbsoluteFilePath,
								baseAbsoluteFilePath, mergeCmd, tempDir);
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
						ToolsUtils.informUserAboutError("mergetool - error", //$NON-NLS-1$
								e.getMessage() + "\n\nMerge aborted!"); //$NON-NLS-1$
						return; // abort the merge process
					} finally {
						System.out.println("exitCode: " //$NON-NLS-1$
								+ Integer.toString(exitCode));
						if (tempDir != null && !keepTemporaries) {
							ToolsUtils.deleteDirectoryForTempFiles(tempDir);
						}
