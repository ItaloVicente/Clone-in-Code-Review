			Predicate<ISelectionModel> additionalPredicate) {
		Assert.isNotNull(part);
		Assert.isNotNull(listener);
		Assert.isNotNull(additionalPredicate);
		return new PartSelectionListener(part, listener,
				alreadyDelivered.and(targetPartVisible).and(additionalPredicate));
