	void storedSymbolicRef(RefDirectoryUpdate u
			throws IOException {
		String name = u.getRef().getName();
		synchronized (cacheLock) {
			RefHolder holder = cache.get(name);
			if (holder != null) {
				if (holder.packRef != null) {
					holder.packRef = null;
					savePackedRefs();
				}
				name = holder.currRef.getName();
			} else {
				holder = new RefHolder();
				cache.put(name
			}

			holder.currRef = newSymbolicRef(name
			holder.looseModified = modified;
			modCnt++;
		}
		fireRefsChanged();
	}

