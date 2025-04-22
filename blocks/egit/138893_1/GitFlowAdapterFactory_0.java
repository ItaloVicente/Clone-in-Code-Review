	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (Repository.class.equals(adapterType)) {
			Repository repository = null;
			if (adaptableObject instanceof IResource) {
				IResource resource = (IResource) adaptableObject;
				repository = getRepository(resource);
			} else if (adaptableObject instanceof IHistoryView) {
				IHistoryView historyView = (IHistoryView) adaptableObject;
				IHistoryPage historyPage = historyView.getHistoryPage();
				Object input = historyPage.getInput();
				if (input instanceof RepositoryNode) {
					RepositoryNode node = (RepositoryNode) input;
					repository = node.getRepository();
				} else if (input instanceof IResource) {
					repository = getRepository((IResource) input);
				}
			} else if (adaptableObject instanceof ISelection) {
				IStructuredSelection structuredSelection = SelectionUtils
						.getStructuredSelection((ISelection) adaptableObject);
				repository = SelectionUtils.getRepository(structuredSelection);
			} else {
				return null;
