	public List<Ref> getRefsByPrefixWithExclusions(String include, Set<String> excludes)
			throws IOException {
		Stream<Ref> refs = getRefs(include).values().stream();
		for(String exclude: excludes) {
			refs = refs.filter(r -> !r.getName().startsWith(exclude));
		}
		return Collections.unmodifiableList(refs.collect(Collectors.toList()));
