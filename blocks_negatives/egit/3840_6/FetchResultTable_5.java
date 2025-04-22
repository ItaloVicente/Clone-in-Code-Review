		final TableViewerColumn statusViewer = createColumn(layout,
				UIText.FetchResultTable_columnStatus, COLUMN_STATUS_WEIGHT,
				SWT.LEFT);
		statusViewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final TrackingRefUpdate tru = (TrackingRefUpdate) element;
				final RefUpdate.Result r = tru.getResult();
				if (r == RefUpdate.Result.LOCK_FAILURE)
					return UIText.FetchResultTable_statusLockFailure;

				if (r == RefUpdate.Result.IO_FAILURE)
					return UIText.FetchResultTable_statusIOError;

				if (r == RefUpdate.Result.NEW) {
					if (tru.getRemoteName().startsWith(Constants.R_HEADS))
						return UIText.FetchResultTable_statusNewBranch;
					else if (tru.getLocalName().startsWith(Constants.R_TAGS))
						return UIText.FetchResultTable_statusNewTag;
					return UIText.FetchResultTable_statusNew;
				}
