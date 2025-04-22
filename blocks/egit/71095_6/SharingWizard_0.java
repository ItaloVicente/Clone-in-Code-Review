	private void closeOpenEditorsForProject(IProject project) {
		final List<IEditorReference> editorRefsToClose = new ArrayList<>();
		Map<IFile, IEditorReference> fileEditors = findAllEditorReferences();
		Set<IFile> keySet = fileEditors.keySet();
		for (IFile file : keySet) {
			if (file.getProject().equals(project)) {
				editorRefsToClose.add(fileEditors.get(file));
			}
		}

		if (editorRefsToClose.isEmpty()) {
			return;
		}
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {

				IEditorReference[] editorsToClose = new IEditorReference[editorRefsToClose
						.size()];
				editorsToClose = editorRefsToClose.toArray(editorsToClose);
				activePage.closeEditors(editorsToClose, true);
			}
		});
	}

	private Map<IFile, IEditorReference> findAllEditorReferences() {
		IEditorReference[] editorReferences = activePage.getEditorReferences();
		Map<IFile, IEditorReference> fileEditors = new HashMap<>();
		for (IEditorReference editorReference : editorReferences) {
			try {
				IEditorInput editorInput = editorReference.getEditorInput();
				if (editorInput instanceof IFileEditorInput) {
					IFileEditorInput fileEditorInput = (IFileEditorInput) editorInput;
					IFile file = fileEditorInput.getFile();
					fileEditors.put(file, editorReference);
				}
			} catch (PartInitException e) {
				Activator.logError("PartInitException - should not happen", e); //$NON-NLS-1$
			}
		}
		return fileEditors;
	}

