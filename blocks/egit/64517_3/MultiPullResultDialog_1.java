		tc = new TableViewerColumn(tv, SWT.NONE);
		col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				if (item.getValue() instanceof IStatus)
					return UIText.MultiPullResultDialog_UnknownStatus;
				PullResult pullRes = (PullResult) item.getValue();
				if (pullRes.getFetchResult() == null) {
					return UIText.MultiPullResultDialog_NothingFetchedStatus;
				} else if (pullRes.getFetchResult().getTrackingRefUpdates()
						.isEmpty()) {
					return UIText.MultiPullResultDialog_NothingUpdatedStatus;
				} else {
					int updated = pullRes.getFetchResult()
							.getTrackingRefUpdates().size();
					if (updated == 1) {
						return UIText.MultiPullResultDialog_UpdatedOneMessage;
					}
					return NLS.bind(UIText.MultiPullResultDialog_UpdatedMessage,
							Integer.valueOf(updated));
				}
			}
		});
