	@Override
	@NonNull
	public RefUpdate updateRef(String ref
		return getRefDatabase().newUpdate(ref
	}

	@Override
	@NonNull
	public RefRename renameRef(String fromRef
			throws IOException {
		return getRefDatabase().newRename(fromRef
	}

