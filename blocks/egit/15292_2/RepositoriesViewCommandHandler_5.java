	private boolean selectionHasHead(boolean all) {
		List<T> selectedNodes = getSelectedNodes();
		if (selectedNodes.size() > 0) {
			if (all) {
				for (T element : selectedNodes)
					if (!repositoryHasHead(element))
						return false;
				return true;
