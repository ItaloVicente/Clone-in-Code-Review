
			private void deleteRecursive(File fileToDelete) throws IOException {
				if (fileToDelete == null)
					return;
				if (fileToDelete.exists()) {
					if (fileToDelete.isDirectory()) {
						for (File file : fileToDelete.listFiles()) {
							deleteRecursive(file);
						}
					}
					if (!fileToDelete.delete())
						throw new IOException(NLS.bind(
								UIText.RemoveCommand_DeleteFailureMessage,
								fileToDelete.getAbsolutePath()));
				}
			}
