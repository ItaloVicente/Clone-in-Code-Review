		final TableViewer v = new TableViewer(shell,SWT.BORDER|SWT.FULL_SELECTION);
		TableColumn tc = new TableColumn(v.getTable(),SWT.NONE);
		tc.setWidth(100);
		tc.setText("Column 1");
		
		tc = new TableColumn(v.getTable(),SWT.NONE);
		tc.setWidth(200);
		tc.setText("Column 2");
		
		v.setLabelProvider(new LabelProvider());
		v.setContentProvider(new MyContentProvider());
		v.setCellModifier(new ICellModifier() {

			/* (non-Javadoc)
			 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
			 */
