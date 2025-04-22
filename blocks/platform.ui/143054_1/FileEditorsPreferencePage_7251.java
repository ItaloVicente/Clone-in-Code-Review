		TableItem item = new TableItem(resourceTypeTable, SWT.NULL, index);
		if (image != null) {
			item.setImage(image);
		}
		item.setText(mapping.getLabel());
		item.setData(mapping);
		if (selected) {
			resourceTypeTable.setSelection(index);
		}

		return item;
	}

	@Override
