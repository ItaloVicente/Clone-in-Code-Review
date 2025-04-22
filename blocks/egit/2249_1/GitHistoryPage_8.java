	private static String calculateName(HistoryPageInput in) {
		final String repositoryName = Activator.getDefault()
				.getRepositoryUtil().getRepositoryName(in.getRepository());
		if (in.getItems() == null && in.getFileList() == null) {
			return NLS.bind(UIText.GitHistoryPage_RepositoryNamePattern,
					repositoryName);
		} else if (in.getItems() != null && in.getItems().length == 1) {
			IResource resource = in.getItems()[0];
			final String type;
			switch (resource.getType()) {
			case IResource.FILE:
				type = UIText.GitHistoryPage_FileType;
				break;
			case IResource.PROJECT:
				type = UIText.GitHistoryPage_ProjectType;
				break;
			default:
				type = UIText.GitHistoryPage_FolderType;
				break;
