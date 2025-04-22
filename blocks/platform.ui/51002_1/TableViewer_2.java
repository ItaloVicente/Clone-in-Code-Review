	@Override
	public void removeAtPosition(Object elementToBeRemoved, int removeIndex) {
		Assert.isNotNull(elementToBeRemoved);

		int[] selectionIndices = doGetSelectionIndices();
		boolean deselectedItem = false;
		for (int selectionIndex : selectionIndices) {
			if (selectionIndex != removeIndex)
				continue;

			Item item = doGetItem(removeIndex);
			Assert.isNotNull(item);

			Object data = item.getData();
			Assert.isNotNull(data);
			String errorMsg = JFaceResources.format("TableViewer.invalidArgument", //$NON-NLS-1$
					new Object[] { elementToBeRemoved, data, Integer.valueOf(removeIndex) });
			Assert.isTrue(equals(elementToBeRemoved, data), errorMsg);

			table.deselect(removeIndex);
			deselectedItem = true;
			break;
		}

		super.removeAtPosition(elementToBeRemoved, removeIndex);

		if (deselectedItem) {
			ISelection sel = getSelection();
			updateSelection(sel);
			firePostSelectionChanged(new SelectionChangedEvent(this, sel));
		}
	}

