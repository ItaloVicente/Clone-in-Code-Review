	private RevCommit selectedCommit() {
		IStructuredSelection selection = graph.getTableView()
				.getStructuredSelection();
		if (!selection.isEmpty()) {
			return Adapters.adapt(selection.getFirstElement(), RevCommit.class);
		}
		return null;
	}

