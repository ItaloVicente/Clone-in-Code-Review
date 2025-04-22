		ISelectionProvider selectionProvider = activePart.getSite()
				.getSelectionProvider();
		ISelection selection = selectionProvider.getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}
		Object element = ((IStructuredSelection) selection).getFirstElement();
		if (!(element instanceof IRepositoryCommit)) {
			return null;
		}
		Repository repository = ((IRepositoryCommit) element).getRepository();

