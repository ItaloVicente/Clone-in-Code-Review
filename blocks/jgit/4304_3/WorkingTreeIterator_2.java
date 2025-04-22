			final byte[] subId = idSubmodule(entries[ptr]);
			if (subId != zeroid) {
				contentIdFromPtr = ptr;
				contentId = subId;
			}
			return subId;
		}
		return zeroid;
	}

	protected byte[] idSubmodule(Entry e) {
		if( repository == null)
			return zeroid;
		File directory;
		try {
			directory = repository.getWorkTree();
		} catch (NoWorkTreeException nwte) {
