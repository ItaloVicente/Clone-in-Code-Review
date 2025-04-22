	private void closeOpenEditorsForProject(IProject project) {
		final List<IEditorReference> editorRefsToClose = new ArrayList<>();
		Map<IFile, IEditorReference> fileEditors = findAllEditorReferences();
		Set<IFile> keySet = fileEditors.keySet();
		for (IFile file : keySet) {
			if (file.getProject().equals(project)) {
				editorRefsToClose.add(fileEditors.get(file));
			}
		}

