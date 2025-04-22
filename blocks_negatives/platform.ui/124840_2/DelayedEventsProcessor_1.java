						IEditorPart openEditor = IDE.openInternalEditorOnFileStore(page, details.fileStore);
						Shell shell = window.getShell();
						if (shell != null) {
							if (shell.getMinimized())
								shell.setMinimized(false);
							shell.forceActive();
						}
