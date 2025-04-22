	public void setAdvertisedRefs(Map<String, Ref> allRefs, Set<ObjectId> additionalHaves) {
		refs = allRefs != null ? allRefs : db.getAllRefs();
		refs = refFilter.filter(refs);

		Ref head = refs.get(Constants.HEAD);
		if (head != null && head.isSymbolic())
			refs.remove(Constants.HEAD);

		for (Ref ref : refs.values()) {
			if (ref.getObjectId() != null)
				advertisedHaves.add(ref.getObjectId());
		}
		if (additionalHaves != null)
			advertisedHaves.addAll(additionalHaves);
		else
			advertisedHaves.addAll(db.getAdditionalHaves());
	}
