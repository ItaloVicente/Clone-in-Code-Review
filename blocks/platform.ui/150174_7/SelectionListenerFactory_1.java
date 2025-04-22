	public static ISelectionListener decorate(ISelectionListener listener,
			Predicate<ISelectionModel> additionalPredicate) {
		Assert.isNotNull(additionalPredicate);
		if (listener instanceof PartSelectionListener) {
			return ((PartSelectionListener) listener).addPredicate(additionalPredicate);
		}
		return listener;
