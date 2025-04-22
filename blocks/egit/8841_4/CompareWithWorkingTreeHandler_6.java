					final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
							SaveableCompareEditorInput.createFileElement(file),
							right, ancestor, null);
					openInCompare(event, in);
				} else {
					try {
						GitModelSynchronize.synchronizeModelWithWorkspace(file,
								repo, commit.getName());
					} catch (IOException e) {
						throw new ExecutionException(e.getMessage(), e);
					}
				}
