	public Snippet029VirtualTableViewer(Shell shell, boolean fullSelect) {
		final TableViewer v = new TableViewer(shell, SWT.VIRTUAL | (fullSelect ? SWT.FULL_SELECTION : 0));
		v.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				cell.setText(cell.getElement().toString());
				super.update(cell);
			}
		});
