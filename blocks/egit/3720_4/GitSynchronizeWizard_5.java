	private Set<IContainer> getSelectedContainers(Repository repo) {
		ISelectionService selectionService = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getSelectionService();
		ISelection selection = selectionService.getSelection();
		if (selection instanceof IStructuredSelection) {
			Set<IContainer> result = new HashSet<IContainer>();
			IStructuredSelection sel = (IStructuredSelection) selection;
			if (sel.size() == 0)
				return null;

			File workTree = repo.getWorkTree();
			for (Object o : sel.toArray()) {
				if (!(o instanceof IAdaptable))
					continue;

				IResource res = (IResource) ((IAdaptable) o)
						.getAdapter(IResource.class);
				if (res == null)
					continue;

				int type = res.getType();
				if (type == IResource.FOLDER || type == IResource.PROJECT) {
					Repository selRepo = RepositoryMapping.getMapping(res)
							.getRepository();
					if (workTree.equals(selRepo.getWorkTree()))
						result.add((IContainer) res);
				}
			}

			return result;
		}
		return null;
	}

