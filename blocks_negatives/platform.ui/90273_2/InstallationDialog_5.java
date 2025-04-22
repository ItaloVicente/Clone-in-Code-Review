	private SelectionAdapter createFolderSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				tabSelected((TabItem) e.item);
			}
		};
