		additionalHaves(walk.getRepository().getObjectDatabase());
	}

	private void additionalHaves(final ObjectDatabase db) throws IOException {
		if (db instanceof AlternateRepositoryDatabase)
			additionalHaves(((AlternateRepositoryDatabase) db).getRepository());
		for (ObjectDatabase alt : db.getAlternates())
			additionalHaves(alt);
	}

	private void additionalHaves(final Repository alt) throws IOException {
		for (final Ref r : alt.getAllRefs().values())
			advertiseHave(r.getObjectId());
