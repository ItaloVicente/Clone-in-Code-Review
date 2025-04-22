		TableItem items[] = editorsTable.getSelection();
		if (items.length != 1) {
			super.okPressed();
			return;
		}

		Adapter selection = (Adapter) items[0].getData();
		super.okPressed();
		selection.activate();
	}
