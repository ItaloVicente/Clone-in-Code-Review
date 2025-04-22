	}

	private void updateItem(int index, int itemPos, int itemCount) {
		ResourceDescriptor desc = descriptors[index];
		TableItem item;
		if (itemPos < itemCount) {
			item = resourceNames.getItem(itemPos);
			if (item.getData() != desc) {
				item.setText(desc.label);
				item.setData(desc);
				item.setImage(getImage(desc));
				if (itemPos == 0) {
					resourceNames.setSelection(0);
					updateFolders(desc);
				}
			}
		} else {
			item = new TableItem(resourceNames, SWT.NONE);
			item.setText(desc.label);
			item.setData(desc);
			item.setImage(getImage(desc));
			if (itemPos == 0) {
				resourceNames.setSelection(0);
				updateFolders(desc);
			}
		}
		updateOKState(true);
	}
