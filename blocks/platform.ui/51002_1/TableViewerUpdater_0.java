
	@Override
	public void move(Object element, int oldPosition, int newPosition) {
		Table table = (Table) viewer.getControl();
		int[] selectionIndices = table.getSelectionIndices();
		super.move(element, oldPosition, newPosition);
		replaceIndex(selectionIndices, oldPosition, newPosition);
		table.setSelection(selectionIndices);
	}

	private void replaceIndex(int[] selectionIndices, int oldPosition, int newPosition) {
		for (int i = 0; i < selectionIndices.length; i++) {
			int j = selectionIndices[i];
			if (j == oldPosition) {
				selectionIndices[i] = newPosition;
				return;
			}
		}
	}
