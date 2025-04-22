			private void tryToCloseEditor(final StashedCommitNode node) {
				Display.getDefault().asyncExec(new Runnable() {

					public void run() {
						IWorkbenchPage activePage = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage();
						IEditorReference[] editorReferences = activePage
								.getEditorReferences();
						for (IEditorReference editorReference : editorReferences) {
							IEditorInput editorInput = null;
							try {
								editorInput = editorReference.getEditorInput();
							} catch (PartInitException e) {
								Activator.handleError(e.getMessage(), e, true);
							}
							if (editorInput instanceof CommitEditorInput) {
								CommitEditorInput comEditorInput = (CommitEditorInput) editorInput;
								if (comEditorInput.getCommit().getRevCommit()
										.equals(node.getObject())) {
									activePage.closeEditor(
											editorReference.getEditor(false),
											false);
								}
							}
						}
					}
				});

			}

