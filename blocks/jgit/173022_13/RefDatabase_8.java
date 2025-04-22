
	@NonNull
	public List<Ref> getRefsByPrefix(String include
			throws IOException {
		Stream<Ref> refs = getRefs(include).values().stream();
		for(String prefixToExclude: exclude) {
			refs = refs.filter(r -> !r.getName().startsWith(prefixToExclude));
		}
		return Collections.unmodifiableList(refs.collect(Collectors.toList()));
	}

