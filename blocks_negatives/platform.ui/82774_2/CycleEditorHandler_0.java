		for (EditorReference ref : refs) {
            TableItem item = null;
            item = new TableItem(table, SWT.NONE);
			if (ref.isDirty()) {
			} else {
				item.setText(ref.getTitle());
			}
			item.setImage(ref.getTitleImage());
			item.setData(ref);
        }
