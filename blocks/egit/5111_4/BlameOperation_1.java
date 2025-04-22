	private void openEditor(final RevisionInformation info) {
		AbstractDecoratedTextEditor editor;
		try {
			if (storage instanceof IFile)
				editor = RevisionAnnotationController.openEditor(page,
						(IFile) storage);
			else
				editor = RevisionAnnotationController.openEditor(page, storage,
						storage);
		} catch (PartInitException e) {
			Activator.handleError("Error displaying blame annotations", e, //$NON-NLS-1$
					false);
			return;
		}
		if (editor == null)
			return;

		try {
			IHistoryView part = (IHistoryView) page.showView(
					IHistoryView.VIEW_ID, null, IWorkbenchPage.VIEW_VISIBLE);
			HistoryPageInput input;
			if (storage instanceof IFile)
				input = new HistoryPageInput(repository,
						new IResource[] { (IResource) storage });
			else if (!repository.isBare())
				input = new HistoryPageInput(repository, new File[] { new File(
						repository.getWorkTree(), path) });
			else
				input = new HistoryPageInput(repository);
			part.showHistoryFor(input);
		} catch (PartInitException e) {
			Activator.handleError("Error displaying blame annotations", e, //$NON-NLS-1$
					false);
		}

		editor.showRevisionInformation(info,
				"org.eclipse.egit.ui.internal.decorators.GitQuickDiffProvider"); //$NON-NLS-1$

		IRevisionRulerColumn revisionRuler = AdapterUtils.adapt(editor,
				IRevisionRulerColumn.class);
		if (revisionRuler instanceof IRevisionRulerColumnExtension)
			((IRevisionRulerColumnExtension) revisionRuler)
					.getRevisionSelectionProvider()
					.addSelectionChangedListener(
							new RevisionSelectionHandler(repository, path,
									storage));
	}

