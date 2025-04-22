		ISelection selection = HandlerUtil.getCurrentSelection(event);
		IEditorInput editorInput = getActiveEditorInput(event);
		return getRepository(selection, editorInput);
	}

	/**
	 * Get repository from selection
	 *
	 * @param selection the selection or <code>null</code> if not available
	 * @param editorInput the editor input to be used in case of a text selection or <code>null</code> if not available
	 * @return a {@link Repository} if all elements in the current selection map
	 *         to the same {@link Repository}, otherwise null
	 */
	protected static Repository getRepository(ISelection selection, IEditorInput editorInput) {
		if (selection == null || selection.isEmpty())
			return null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Repository result = null;
			for (Object element : ssel.toList()) {
				Repository elementRepository = null;
				if (element instanceof RepositoryTreeNode)
					elementRepository = ((RepositoryTreeNode) element)
							.getRepository();
				else if (element instanceof IResource) {
					IResource resource = (IResource) element;
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(resource.getProject());
					if (mapping != null)
						elementRepository = mapping.getRepository();
				} else if (element instanceof IAdaptable) {
					IResource adapted = (IResource) ((IAdaptable) element)
							.getAdapter(IResource.class);
					if (adapted != null) {
						RepositoryMapping mapping = RepositoryMapping
								.getMapping(adapted.getProject());
						if (mapping != null)
							elementRepository = mapping.getRepository();
					}
				}
				if (elementRepository == null)
					continue;
				if (result != null && !elementRepository.equals(result))
					return null;
				if (result == null)
					result = elementRepository;
			}
			return result;
		}
		if (selection instanceof TextSelection && editorInput != null) {
			IResource resource = (IResource) editorInput
					.getAdapter(IResource.class);
			if (resource != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				if (mapping != null)
					return mapping.getRepository();
			}
		}
