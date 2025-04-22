		return getRefsStreamByPrefix(prefix)
				.collect(Collectors.toUnmodifiableList());
	}

	@NonNull
	public Stream<Ref> getRefsStreamByPrefix(String prefix) throws IOException {
