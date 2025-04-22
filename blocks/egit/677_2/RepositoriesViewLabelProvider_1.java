			if (node.getRepository().getConfig().getBoolean(
					"core", "bare", false)) //$NON-NLS-1$ //$NON-NLS-2$
				return UIText.RepositoriesView_WorkingDir_treenode
						+ " - " //$NON-NLS-1$
						+ UIText.RepositoriesViewLabelProvider_BareRepositoryMessage;
			else
				return UIText.RepositoriesView_WorkingDir_treenode + " - " //$NON-NLS-1$
						+ node.getRepository().getWorkDir().getAbsolutePath();
