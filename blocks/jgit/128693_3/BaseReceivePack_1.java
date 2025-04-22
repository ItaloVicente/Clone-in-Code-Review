	@Deprecated
	public final void setAdvertisedRefs(
			Map<String
		Collection<Ref> r = allRefs == null ? null : allRefs.values();
		try {
			setAdvertisedRefs(r
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public void setAdvertisedRefs(@Nullable Collection<Ref> allRefs
			@Nullable Set<ObjectId> additionalHaves) throws IOException {
		if (allRefs != null) {
			refs = allRefs.stream().collect(
					toMap(Ref::getName
		} else {
			refs = db.getRefDatabase().getRefs(ALL);
		}
