		
		TreeColumn column = new TreeColumn(v.getTree(),SWT.NONE);
		column.setWidth(200);
		column.setText("Column 1");
		ad.setColumnData(column, new ColumnWeightData(50, 100));
		
		column = new TreeColumn(v.getTree(),SWT.NONE);
		column.setWidth(200);
		column.setText("Column 2");
		ad.setColumnData(column,new ColumnWeightData(50, 100));
		
