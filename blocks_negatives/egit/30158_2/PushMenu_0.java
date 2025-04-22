		if (this.selectionService != null
				&& this.selectionService.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) this.selectionService
					.getSelection();
			Object selected = sel.getFirstElement();
			if (selected instanceof IAdaptable) {
				Object adapter = ((IAdaptable) selected)
						.getAdapter(IProject.class);
				if (adapter != null)
					selected = adapter;
			}

			Repository repository = null;
			if (selected instanceof RepositoryNode)
				repository = ((RepositoryNode) selected).getRepository();
			else if (selected instanceof BranchesNode)
				repository = ((BranchesNode) selected).getRepository();
			else if (selected instanceof LocalNode)
				repository = ((LocalNode) selected).getRepository();
			else if ((selected instanceof IProject)) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping((IProject) selected);
				if (mapping != null)
					repository = mapping.getRepository();
			}
