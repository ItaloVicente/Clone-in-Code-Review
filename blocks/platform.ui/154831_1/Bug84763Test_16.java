		contextManagerListener = contextManagerEvent -> {
previousContextIds = contextManagerEvent
			.getPreviouslyActiveContextIds();
if (previousContextIds != null) {
		previousContextIds = new HashSet<>(previousContextIds);
}
};
