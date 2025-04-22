	public void pack(String[] refs) throws IOException {
		if (refs.length == 0)
			return;
		FS fs = parent.getFS();
		LockFile[] locks = new LockFile[refs.length];

		LockFile lck = new LockFile(packedRefsFile
		if (!lck.lock())
			throw new IOException(MessageFormat.format(
					JGitText.get().cannotLock

		try {
			String refName;
			LockFile rLck;
			final PackedRefList packed = getPackedRefs();
			RefList<Ref> cur = readPackedRefs();

			for (int i = 0; i < refs.length; i++) {
				refName = refs[i];
				Ref ref = readRef(refName
				if (ref.isSymbolic())
				if (ref.getStorage().isLoose()) {
					rLck = new LockFile(fileFor(refName)
							parent.getFS());
					if (!rLck.lock())
						continue;
					locks[i] = rLck;
				}
				int idx = cur.find(refName);
				if (idx >= 0) {
					cur = cur.set(idx
				} else
					cur = cur.add(idx

			}

			commitPackedRefs(lck

			for (int i = 0; i < refs.length; i++) {
				rLck = locks[i];
				if (rLck == null)
					continue;
				refName = refs[i];
				RefList<LooseRef> curLoose
				do {
					curLoose = looseRefs.get();
					int idx = curLoose.find(refName);
					if (idx < 0)
						break;
					newLoose = curLoose.remove(idx);
				} while (!looseRefs.compareAndSet(curLoose
				int levels = levelsIn(refName) - 2;
				delete(fileFor(refName)
				locks[i] = null;
				rLck.unlock();
			}
		} finally {
			lck.unlock();
			for (LockFile l : locks)
				if (l != null)
					l.unlock();
		}
	}

	private Ref peeledPackedRef(Ref f)
			throws MissingObjectException
		if (f.getStorage().isPacked() && f.isPeeled())
			return f;
		if (!f.isPeeled())
			f = peel(f);
		if (f.getPeeledObjectId() != null)
			return new ObjectIdRef.PeeledTag(PACKED
					f.getObjectId()
		else
			return new ObjectIdRef.PeeledNonTag(PACKED
					f.getObjectId());
	}

