		if (repo instanceof FileRepository) {
			GC gc = new GC((FileRepository) repo);
			try {
				RepoStatistics stats = gc.getStatistics();

				table.setLinesVisible(true);
				table.setHeaderVisible(true);
				GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
				data.heightHint = 200;
				table.setLayoutData(data);

				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, UIText.RepositoryStatistics_NrOfObjects);
				item.setText(1, bigIntFmt.format(stats.numberOfLooseObjects));
				item.setText(2, bigIntFmt.format(stats.numberOfPackedObjects));

				item = new TableItem(table, SWT.NONE);
				item.setText(0, UIText.RepositoryStatistics_NrOfPackfiles);
				item.setText(2, bigIntFmt.format(stats.numberOfPackFiles)
						.toString());

				item = new TableItem(table, SWT.NONE);
				item.setText(0, UIText.RepositoryStatistics_NrOfRefs);
				item.setText(1, bigIntFmt.format(stats.numberOfLooseRefs)
						.toString());
				item.setText(2, bigIntFmt.format(stats.numberOfPackedRefs)
						.toString());

				item = new TableItem(table, SWT.NONE);
				item.setText(0,
						UIText.RepositoryStatistics_SpaceNeededOnFilesystem);
				item.setText(1, describeSize(stats.sizeOfLooseObjects));
				item.setText(2, describeSize(stats.sizeOfPackedObjects));

				for (int i = 0; i < titles.length; i++) {
					table.getColumn(i).pack();
				}
				parent.pack();
			} catch (IOException e) {
