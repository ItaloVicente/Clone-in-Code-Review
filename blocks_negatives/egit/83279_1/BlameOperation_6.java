			if (storage instanceof IFile)
				editor = RevisionAnnotationController.openEditor(page,
						(IFile) storage);
			else
				editor = RevisionAnnotationController.openEditor(page, storage,
						storage);
		} catch (PartInitException e) {
