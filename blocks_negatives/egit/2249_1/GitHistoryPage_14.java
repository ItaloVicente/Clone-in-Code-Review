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
			}
			String path = resource.getFullPath().makeRelative().toString();
			if (resource.getType() == IResource.FOLDER)
				path = path + '/';
			return NLS.bind(NAME_PATTERN, new Object[] { type, path,
					repositoryName });
		} else if (in.getFileList() != null && in.getFileList().length == 1) {
			File resource = in.getFileList()[0];
			String path;
			final String type;
			if (resource.isDirectory()) {
				type = UIText.GitHistoryPage_FolderType;
				path = resource.getPath() + IPath.SEPARATOR;
			} else {
				type = UIText.GitHistoryPage_FileType;
				path = resource.getPath();
			}
			return NLS.bind(NAME_PATTERN, new Object[] { type, path,
					repositoryName });
