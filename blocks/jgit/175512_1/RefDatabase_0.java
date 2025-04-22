	public List<Ref> getRefsByPrefixWithExclusions(String include
			Set<String> excludes) throws IOException {
		Collection<Ref> refs = getRefs(include).values();
		Stream<Ref> refsStream = refs.stream().filter(r -> !excludes.stream()
				.anyMatch(e -> e.startsWith(r.getName())));
		return Collections.unmodifiableList(refsStream
				.collect(toCollection(() -> new ArrayList<>(refs.size()))));
