	public RepositoryGroup getSelectedRepositoryGroup(ExecutionEvent event)
			throws ExecutionException {
		List<?> selected = getSelectedNodes(event);
		if (selected.size() == 1
				&& selected.get(0) instanceof RepositoryGroupNode) {
			return ((RepositoryGroupNode) selected.get(0)).getObject();
		}
		return null;
	}

