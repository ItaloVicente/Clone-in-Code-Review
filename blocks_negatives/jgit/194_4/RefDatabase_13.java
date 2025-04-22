	RefUpdate newUpdate(final String name, boolean detach) throws IOException {
		refreshPackedRefs();
		Ref r = readRefBasic(name, 0);
		if (r == null)
			r = new Ref(Ref.Storage.NEW, name, null);
		else if (detach)
			r = new Ref(Ref.Storage.NEW, name, r.getObjectId());
		return new RefUpdate(this, r, fileForRef(r.getName()));
	}

	void stored(final String origName, final String name, final ObjectId id, final long time) {
		synchronized (this) {
			looseRefs.put(name, new Ref(Ref.Storage.LOOSE, name, name, id));
			looseRefsMTime.put(name, time);
			setModified();
		}
		db.fireRefsMaybeChanged();
	}
