			if (sel.isEmpty()) {
				setErrorMessage(UIText.GitImportWithDirectoriesPage_SelectFolderMessage);
				return;
			}
			RepositoryTreeNode node = (RepositoryTreeNode) sel
					.getFirstElement();
			if (node.getType() != RepositoryTreeNodeType.FOLDER
					&& node.getType() != RepositoryTreeNodeType.WORKINGDIR) {
				setErrorMessage(UIText.GitImportWithDirectoriesPage_SelectFolderMessage);
				return;
			}
