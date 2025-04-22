					AbstractDecoratedTextEditor editor;
					if (storage instanceof IFile)
						editor = RevisionAnnotationController.openEditor(page,
								(IFile) storage);
					else
						editor = RevisionAnnotationController.openEditor(page,
								storage, storage);
