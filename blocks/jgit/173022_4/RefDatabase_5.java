	@NonNull
	public List<Ref> getRefsExcludingPrefixes(Set<String> prefixes) throws IOException {
		Stream<Ref> refs = getRefs(ALL).values().stream();
		for(String prefix: prefixes) {
			refs = refs.filter(r -> !r.getName().startsWith(prefix));
		}
		return Collections.unmodifiableList(refs.collect(Collectors.toList()));
	}

