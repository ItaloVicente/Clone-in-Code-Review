	@NonNull
	public Collection<Ref> getAllTags() {
		try {
			return getRefDatabase().getRefsByPrefix(Constants.R_TAGS);
		} catch (IOException e) {
			return new ArrayList<>();
		}
	}

