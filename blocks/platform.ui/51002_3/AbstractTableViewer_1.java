	public void removeAtPosition(Object elementToBeRemoved, final int removeIndex) {
		if (checkBusy())
			return;
		if (elementToBeRemoved == null || removeIndex < 0)
			return;

		Item item = doGetItem(removeIndex);

		boolean isVirtualParent = (getControl().getStyle() & SWT.VIRTUAL) != 0;
		if (!isVirtualParent) {
			Assert.isTrue(equals(item.getData(), elementToBeRemoved));
			Assert.isNotNull(item.getData());
			disassociate(item);
		} else if (item.getData() != null) {
			Assert.isTrue(equals(item.getData(), elementToBeRemoved));
			disassociate(item);
		}
		if (virtualManager != null) {
			virtualManager.removeIndices(new int[] { removeIndex });
		}
		doRemove(new int[] { removeIndex });
	}

