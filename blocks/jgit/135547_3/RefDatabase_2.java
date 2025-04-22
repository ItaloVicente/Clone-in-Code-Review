
	@NonNull
	public Set<Ref> resolveTipSha1(ObjectId id) throws IOException {
		return getRefs().stream()
				.filter(r -> id.equals(r.getObjectId())
						|| id.equals(r.getPeeledObjectId()))
				.collect(Collectors.toSet());
	}

