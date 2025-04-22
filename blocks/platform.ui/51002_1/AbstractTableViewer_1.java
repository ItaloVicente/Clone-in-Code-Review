	public void removeAtPosition(Object elementToBeRemoved, final int removeIndex) {
		if (checkBusy())
			return;
		if (elementToBeRemoved == null || removeIndex < 0)
			return;

		Item item = doGetItem(removeIndex);
		if (equals(item.getData(), elementToBeRemoved)) {
			disassociate(item);
			if (virtualManager != null) {
				virtualManager.removeIndices(new int[] { removeIndex });
			}
			doRemove(new int[] { removeIndex });
		}
	}

