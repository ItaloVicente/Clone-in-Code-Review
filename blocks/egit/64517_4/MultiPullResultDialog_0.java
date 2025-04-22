		TableViewerColumn tc = new TableViewerColumn(tv, SWT.NONE);
		TableColumn col = tc.getColumn();
		tc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				@SuppressWarnings("unchecked")
				Entry<Repository, Object> item = (Entry<Repository, Object>) element;
				return utils.getRepositoryName(item.getKey());
			}
		});
