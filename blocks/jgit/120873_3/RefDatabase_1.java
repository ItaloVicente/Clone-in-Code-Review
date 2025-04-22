	@NonNull
	public List<Ref> getRefsByPrefix(String prefix) throws IOException {
		Map<String
		int lastSlash = prefix.lastIndexOf('/');
		if (lastSlash == -1) {
			coarseRefs = getRefs(ALL);
		} else {
			coarseRefs = getRefs(prefix.substring(0
		}

		List<Ref> result;
		if (lastSlash + 1 == prefix.length()) {
			result = coarseRefs.values().stream().collect(toList());
		} else {
			String p = prefix.substring(lastSlash + 1);
			result = coarseRefs.entrySet().stream()
					.filter(e -> e.getKey().startsWith(p))
					.map(e -> e.getValue())
					.collect(toList());
		}
		return Collections.unmodifiableList(result);
	}

