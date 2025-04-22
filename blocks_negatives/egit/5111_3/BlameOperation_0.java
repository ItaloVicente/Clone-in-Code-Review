				try {
					AbstractDecoratedTextEditor editor;
					if (storage instanceof IFile)
						editor = RevisionAnnotationController.openEditor(page,
								(IFile) storage);
					else
						editor = RevisionAnnotationController.openEditor(page,
								storage, storage);
					if (editor != null)
						editor.showRevisionInformation(info,
				} catch (PartInitException e) {
					Activator.handleError(
							"Error displaying blame annotations", e, //$NON-NLS-1$
							false);
				}
