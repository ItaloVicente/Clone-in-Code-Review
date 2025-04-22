	void link(final String name, final String target) throws IOException {
		final byte[] content = Constants.encode("ref: " + target + "\n");
		lockAndWriteFile(fileForRef(name), content);
		synchronized (this) {
			looseSymRefs.remove(name);
			setModified();
		}
		db.fireRefsMaybeChanged();
	}

	void uncacheSymRef(String name) {
		synchronized(this) {
			looseSymRefs.remove(name);
			setModified();
		}
	}

	void uncacheRef(String name) {
		looseRefs.remove(name);
		looseRefsMTime.remove(name);
		packedRefs.remove(name);
	}

	private void setModified() {
		lastRefModification = refModificationCounter++;
	}

	Ref readRef(final String partialName) throws IOException {
		refreshPackedRefs();
		for (int k = 0; k < refSearchPaths.length; k++) {
			final Ref r = readRefBasic(refSearchPaths[k] + partialName, 0);
			if (r != null && r.getObjectId() != null)
				return r;
		}
		return null;
	}
