					if (repository != null) {
						if (addFile && GitPreferenceRoot.autoAddToIndex()
								&& mergedAbsoluteFilePath != null) {
							System.out.println("addFile: " //$NON-NLS-1$
									+ mergedFileName);
							Git git = new Git(repository);
