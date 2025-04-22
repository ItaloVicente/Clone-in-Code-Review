				item = new TableItem(table, SWT.NONE);
				item.setText(0, UIText.RepositoryStatistics_NrOfRefs);
				item.setText(1, bigIntFmt.format(stats.numberOfLooseRefs)
						.toString());
				item.setText(2, bigIntFmt.format(stats.numberOfPackedRefs)
						.toString());
