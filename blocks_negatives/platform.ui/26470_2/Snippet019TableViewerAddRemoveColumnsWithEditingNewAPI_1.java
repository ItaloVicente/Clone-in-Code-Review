		TableViewerColumn column = new TableViewerColumn(v,SWT.NONE);
		column.setLabelProvider(new GivenNameLabelProvider());
		column.setEditingSupport(new GivenNameEditing(v));
		
		column.getColumn().setWidth(200);
		column.getColumn().setText("Givenname");
		column.getColumn().setMoveable(true);

		column = new TableViewerColumn(v,SWT.NONE);
		column.setLabelProvider(new SurNameLabelProvider());
		column.setEditingSupport(new SurNameEditing(v));
		column.getColumn().setWidth(200);
		column.getColumn().setText("Surname");
		column.getColumn().setMoveable(true);
