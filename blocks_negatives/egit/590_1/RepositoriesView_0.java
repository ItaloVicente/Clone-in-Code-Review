					IFileStore store = EFS.getLocalFileSystem().getStore(
							new Path(file.getAbsolutePath()));
					try {
						IDE.openEditor(getSite().getPage(),
								new FileStoreEditorInput(store),
								EditorsUI.DEFAULT_TEXT_EDITOR_ID);

					} catch (PartInitException e1) {
						MessageDialog.openError(getSite().getShell(),
								UIText.RepositoriesView_Error_WindowTitle, e1
										.getMessage());
					}

