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
