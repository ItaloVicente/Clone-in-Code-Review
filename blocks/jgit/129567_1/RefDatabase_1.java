	@NonNull
	public Set<Ref> getRefsByPrefixes(Collection<String> prefixes)
			throws IOException {
		Set<Ref> result = new LinkedHashSet<>();
		if (prefixes.isEmpty()) {
			result.addAll(getRefsByPrefix(ALL));
		} else {
			for (String prefix : prefixes) {
				result.addAll(getRefsByPrefix(prefix));
			}
		}
		return Collections.unmodifiableSet(result);
	}

