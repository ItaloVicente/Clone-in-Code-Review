	}

	protected int[] getSelectionIndices() {
		Assert.isNotNull(fFilteredList);
		return fFilteredList.getSelectionIndices();
	}

	protected int getSelectionIndex() {
		Assert.isNotNull(fFilteredList);
		return fFilteredList.getSelectionIndex();
	}

	protected void setSelection(Object[] selection) {
		Assert.isNotNull(fFilteredList);
		fFilteredList.setSelection(selection);
	}

	protected Object[] getSelectedElements() {
		Assert.isNotNull(fFilteredList);
		return fFilteredList.getSelection();
	}

	public Object[] getFoldedElements(int index) {
		Assert.isNotNull(fFilteredList);
		return fFilteredList.getFoldedElements(index);
	}

	@Override
