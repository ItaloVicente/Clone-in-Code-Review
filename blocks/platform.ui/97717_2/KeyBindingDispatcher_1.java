	private Collection<Binding> getExecutableMatches(KeySequence keySequence, IEclipseContext context2) {
		Binding binding = getBindingService().getPerfectMatch(keySequence);
		if (binding != null) {
			return Collections.singleton(binding);
		}
		Collection<Binding> conflicts = getBindingService().getConflictsFor(keySequence);
		if (conflicts != null) {
			return conflicts.stream()
					.filter(match -> getHandlerService().canExecute(match.getParameterizedCommand(), context))
					.collect(Collectors.toList());
		}
		return Collections.emptySet();
