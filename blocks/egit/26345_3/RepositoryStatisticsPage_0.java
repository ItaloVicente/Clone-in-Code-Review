			item = new TableItem(table, SWT.NONE);
			item.setText(0, UIText.RepositoryStatistics_SpaceNeededOnFilesystem);
			item.setText(1,
					describeSize((Long) stats.get("sizeOfLooseObjects"))); //$NON-NLS-1$
			item.setText(2,
					describeSize((Long) stats.get("sizeOfPackedObjects"))); //$NON-NLS-1$
