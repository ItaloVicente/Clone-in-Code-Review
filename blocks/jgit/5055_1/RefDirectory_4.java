	public void pack(Collection<RefDirectoryUpdate> refUpdates) throws IOException {
		if (refUpdates.isEmpty())
			return;
		FS fs = refUpdates.iterator().next().getRepository().getFS();

		Set<RefDirectoryUpdate> lockedLooseRefs = new HashSet<RefDirectoryUpdate>();

		LockFile lck = new LockFile(packedRefsFile
		if (!lck.lock())
			throw new IOException(MessageFormat.format(
					JGitText.get().cannotLockFile

		RevWalk rw = null;
		try {
			rw = new RevWalk(getRepository());
			final PackedRefList packed = getPackedRefs();
			RefList<Ref> cur = readPackedRefs();

			for (RefDirectoryUpdate ru : refUpdates) {
				Ref ref = ru.getRef();
				if (!ref.isSymbolic()) {
					if (ref.getStorage().isLoose()) {
						if (!ru.tryLock(false))
							continue;
						lockedLooseRefs.add(ru);
					}
					int idx = cur.find(ref.getName());
					if (idx >= 0) {
						cur = cur.set(idx
					} else
						cur = cur.add(idx
				}
			}

			commitPackedRefs(lck

			for (RefDirectoryUpdate ru : lockedLooseRefs) {
				String name = ru.getName();
				RefList<LooseRef> curLoose
				do {
					curLoose = looseRefs.get();
					int idx = curLoose.find(name);
					if (idx < 0)
						break;
					newLoose = curLoose.remove(idx);
				} while (!looseRefs.compareAndSet(curLoose
				int levels = levelsIn(name) - 2;
				if (ru.getRef().getStorage().isLoose()) {
					ru.unlock();
					delete(fileFor(name)
				}
			}
			lockedLooseRefs.clear();
		} finally {
			rw.release();
			lck.unlock();
			for (RefDirectoryUpdate ru : lockedLooseRefs)
				ru.unlock();
		}
	}

	private Ref peeledPackedRef(Ref f
			throws MissingObjectException

		if (f.getStorage().isPacked() && f.isPeeled())
			return f;

		RevObject obj = rw.parseAny(f.getObjectId());
		if (obj instanceof RevTag)
			return new ObjectIdRef.PeeledTag(PACKED
					f.getObjectId()
							.peel(obj).copy()));
		else
			return new ObjectIdRef.PeeledNonTag(PACKED
					f.getObjectId());
	}

