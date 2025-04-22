		final TreeViewer v = new TreeViewer(shell,SWT.FULL_SELECTION);
		
		TreeColumn column = new TreeColumn(v.getTree(),SWT.NONE);
		column.setWidth(200);
		column.setText("Column 1");
		
		column = new TreeColumn(v.getTree(),SWT.NONE);
		column.setWidth(200);
		column.setText("Column 2");
		
		v.setCellEditors(new CellEditor[]{new TextCellEditor(v.getTree()), new TextCellEditor(v.getTree())});
		v.setColumnProperties(new String[]{"col1",col2});
		v.setCellModifier(new ICellModifier() {
