		reposColumn.getColumn().setImage(repositoryImage);
		ColumnViewerToolTipSupport.enableFor(treeViewer);
		reposColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getToolTipText(Object element) {
				if (element instanceof SyncRepoEntity)
					return ((Repository) element).getDirectory().getAbsolutePath();

				return null;
			}

