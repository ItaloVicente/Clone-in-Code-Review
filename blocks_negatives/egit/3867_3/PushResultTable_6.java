		int i = 0;
		for (final URIish uri : result.getURIs()) {
			final TableViewerColumn statusViewer = createColumn(layout, NLS
					.bind(UIText.PushResultTable_columnStatusRepo, Integer
							.toString(++i)), COLUMN_STATUS_WEIGHT, SWT.LEFT);
			statusViewer.getColumn().setToolTipText(uri.toString());
			statusViewer.setLabelProvider(new UpdateStatusLabelProvider(uri));
		}
		tableViewer.setInput(result);
