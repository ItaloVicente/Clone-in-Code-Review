	public static ISelectionListener decorate(ISelectionListener listener, Predicate<ISelectionModel> predicate) {
		Assert.isNotNull(predicate);
		if (listener instanceof PartSelectionListener) {
			return ((PartSelectionListener) listener).addPredicate(predicate);
		}
		return listener;
