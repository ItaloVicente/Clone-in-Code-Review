		if (list != null) {
			list.setFocus();
		}
	}

	private void swap(boolean up) {
		setPresentsDefaultValue(false);
		int index = list.getSelectionIndex();
		int target = up ? index - 1 : index + 1;

		if (index >= 0) {
			String[] selection = list.getSelection();
			Assert.isTrue(selection.length == 1);
			list.remove(index);
			list.add(selection[0], target);
			list.setSelection(target);
		}
		selectionChanged();
	}

	private void upPressed() {
		swap(true);
	}

	@Override
