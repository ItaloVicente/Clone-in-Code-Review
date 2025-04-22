	private TableViewerColumn createColumn(
			final TableColumnLayout columnLayout, final String text,
			final int weight, final int style) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(
				reflogTableViewer, style);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(text);
		columnLayout.setColumnData(column, new ColumnWeightData(weight, 10));
		return viewerColumn;
	}

	private static String getRepositoryName(Repository repository) {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		RepositoryState state = repository.getRepositoryState();
		if (state != RepositoryState.SAFE)
			return repoName + '|' + state.getDescription();
		else
			return repoName;
	}

