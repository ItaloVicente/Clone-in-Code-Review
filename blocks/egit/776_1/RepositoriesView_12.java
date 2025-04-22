		for (final RepositoryTreeNode<Repository> node : input) {

			Repository repository = node.getRepository();
			repository.addRepositoryChangedListener(listener);
			try {
				repository.scanForRepoChanges();
			} catch (IOException e1) {
			} finally {
				repository.removeRepositoryChangedListener(listener);
			}
		}

		return !reposToRefresh.isEmpty();
	}

	private void reactOnSelection(ISelection selection) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) selection;
			if (ssel.size() != 1)
				return;
			if (ssel.getFirstElement() instanceof IResource) {
				showResource((IResource) ssel.getFirstElement());
			}
			if (ssel.getFirstElement() instanceof IAdaptable) {
				IResource adapted = (IResource) ((IAdaptable) ssel
						.getFirstElement()).getAdapter(IResource.class);
				if (adapted != null)
					showResource(adapted);
			}
