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
		} else {
			int count = 0;
			StringBuilder b = new StringBuilder();
			if (in.getItems() != null) {
				count = in.getItems().length;
				for (IResource res : in.getItems()) {
					b.append(res.getFullPath());
					if (res.getType() == IResource.FOLDER)
						b.append('/');
					if (b.length() > 100) {
						b.append("...  "); //$NON-NLS-1$
						break;
					}
					b.append(", "); //$NON-NLS-1$
