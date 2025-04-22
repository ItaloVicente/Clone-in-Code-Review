		List<RepositoryCommit> commits = new LinkedList<RepositoryCommit>();
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || selection.isEmpty())
			selection = HandlerUtil.getActiveMenuSelection(event);
		if (selection == null || selection.isEmpty()) {
			IEditorPart editor = HandlerUtil.getActiveEditor(event);
			if (editor != null)
				selection = new StructuredSelection(editor);
		}
		if (selection instanceof IStructuredSelection && !selection.isEmpty())
			for (Object selected : ((IStructuredSelection) selection).toArray())
				if (selected instanceof RepositoryCommit)
					commits.add((RepositoryCommit) selected);
				else if (selected instanceof IAdaptable) {
					selected = ((IAdaptable) selected)
							.getAdapter(RepositoryCommit.class);
					if (selected != null)
						commits.add((RepositoryCommit) selected);
				}
		return commits;
