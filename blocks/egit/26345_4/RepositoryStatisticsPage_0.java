		Git git = new Git(repo);
		GarbageCollectCommand gc = git.gc();
		try {
			Properties stats = gc.getStatistics();

			table.setLinesVisible(true);
			table.setHeaderVisible(true);
			GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.heightHint = 200;
			table.setLayoutData(data);

			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, UIText.RepositoryStatistics_NrOfObjects);
			item.setText(1, getStatsAsString(stats, "numberOfLooseObjects")); //$NON-NLS-1$
			item.setText(2, getStatsAsString(stats, "numberOfPackedObjects")); //$NON-NLS-1$

			item = new TableItem(table, SWT.NONE);
			item.setText(0, UIText.RepositoryStatistics_NrOfPackfiles);
			item.setText(2, getStatsAsString(stats, "numberOfPackFiles")); //$NON-NLS-1$

			item = new TableItem(table, SWT.NONE);
			item.setText(0, UIText.RepositoryStatistics_NrOfRefs);
			item.setText(1, getStatsAsString(stats, "numberOfLooseRefs")); //$NON-NLS-1$
			item.setText(2, getStatsAsString(stats, "numberOfPackedRefs"));//$NON-NLS-1$

			item = new TableItem(table, SWT.NONE);
			item.setText(0, UIText.RepositoryStatistics_SpaceNeededOnFilesystem);
			item.setText(1,
					describeSize(getStatsAsLong(stats, "sizeOfLooseObjects"))); //$NON-NLS-1$
			item.setText(2,
					describeSize(getStatsAsLong(stats, "sizeOfPackedObjects"))); //$NON-NLS-1$

			for (int i = 0; i < titles.length; i++) {
				table.getColumn(i).pack();
