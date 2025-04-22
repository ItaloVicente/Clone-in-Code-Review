				TableColumn[] columns = selectionGroup.getListTable().getColumns();
				for (TableColumn column : columns) {
					column.pack();
				}
			}
		});

		return composite;
	}

	private ITreeContentProvider getResourceProvider(final int resourceType) {
		return new WorkbenchContentProvider() {
			@Override
