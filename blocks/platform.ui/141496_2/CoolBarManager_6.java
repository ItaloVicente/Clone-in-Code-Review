		Assert.isNotNull(item);
		super.itemAdded(item);
		int insertedAt = indexOf(item);
		boolean replaced = false;
		final int size = cbItemsCreationOrder.size();
		for (int i = 0; i < size; i++) {
			IContributionItem created = cbItemsCreationOrder.get(i);
			if (created.getId() != null && created.getId().equals(item.getId())) {
				cbItemsCreationOrder.set(i, item);
				replaced = true;
				break;
			}
		}

		if (!replaced) {
			cbItemsCreationOrder.add(Math.min(Math.max(insertedAt, 0), cbItemsCreationOrder.size()), item);
		}
	}

	@Override
