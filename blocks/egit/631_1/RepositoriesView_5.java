			new MenuItem(men, SWT.SEPARATOR);
			createCopyPathItem(men, path);
		}

		if (node.getType() == RepositoryTreeNodeType.FOLDER) {
			String path = ((File) node.getObject()).getPath();
			createImportProjectItem(men, node.getRepository(), path);
			new MenuItem(men, SWT.SEPARATOR);
			createCopyPathItem(men, path);
