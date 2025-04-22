	public NameRevCommand addAnnotatedTags() {
		checkCallable();
		try {
			for (Ref ref : repo.getRefDatabase().getRefs(Constants.R_TAGS).values()) {
				ObjectId id = ref.getObjectId();
				if (id != null && (walk.parseAny(id) instanceof RevTag))
					addRef(ref);
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		return this;
	}

	public NameRevCommand addRef(Ref ref) {
		checkCallable();
		refs.add(ref);
		return this;
	}

