	@NonNull
	public List<Ref> getRefsByPrefix(String... prefixes) throws IOException {
		List<Ref> result = new ArrayList<>();
		for (String prefix : prefixes) {
			result.addAll(getRefsByPrefix(prefix));
		}
		return Collections.unmodifiableList(result);
	}

