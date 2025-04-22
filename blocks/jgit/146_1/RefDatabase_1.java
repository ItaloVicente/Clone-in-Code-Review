		if (detach) {
			if (r == null)
				r = new Ref(Ref.Storage.NEW
			else {
				r = peel(r);
				r = new Ref(Ref.Storage.NEW
			}
		} else {
			if (r == null)
				r = new Ref(Ref.Storage.NEW
		}
		return new RefUpdate(this
	}

	RefUpdate newUpdateDetached(final String name) throws IOException {
		refreshPackedRefs();
		Ref r = new Ref(Ref.Storage.NEW
