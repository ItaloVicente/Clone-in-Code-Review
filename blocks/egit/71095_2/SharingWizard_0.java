	private void closeOpenEditorsForProject(IProject project) {

		final List<IEditorReference> editorsToBeClosed = new ArrayList<>();
		Map<IFile, IEditorReference> fileEditors = findAllEditorReferences();
		try {
			List<IFile> projectfiles = findAllProjectFiles(project);
			for (IResource resource : projectfiles) {
				if (fileEditors.containsKey(resource)) {
					editorsToBeClosed.add(fileEditors.get(resource));
				}
			}
		} catch (CoreException e) {
			Activator.logError("Problems during auto-close of related editors", //$NON-NLS-1$
					e);
		}


		if (!editorsToBeClosed.isEmpty()) {
			PlatformUI.getWorkbench().getDisplay()
			.syncExec(new Runnable() {
				@Override
				public void run() {
							IEditorReference[] editorToBeClosed = new IEditorReference[editorsToBeClosed
									.size()];
							editorToBeClosed = editorsToBeClosed
									.toArray(editorToBeClosed);
							activePage.closeEditors(editorToBeClosed, true);
					}
					});
		}

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

	private List<IFile> findAllProjectFiles(IContainer container)
			throws CoreException {
		IResource[] members = container.members();
		List<IFile> list = new ArrayList<>();

		for (IResource member : members) {
			if (member instanceof IContainer) {
				IContainer c = (IContainer) member;
				list.addAll(findAllProjectFiles(c));
			} else if (member instanceof IFile) {
				list.add((IFile) member);
			}
		}
		return list;
	}

