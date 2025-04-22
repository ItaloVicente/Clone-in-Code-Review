	public void pack(List<String> refs) throws IOException {
		if (refs.size() == 0)
			return;
		FS fs = parent.getFS();

		LockFile lck = new LockFile(packedRefsFile
		if (!lck.lock())
			throw new IOException(MessageFormat.format(
					JGitText.get().cannotLock

		try {
			final PackedRefList packed = getPackedRefs();
			RefList<Ref> cur = readPackedRefs();

			for (String refName : refs) {
				Ref ref = readRef(refName
				if (ref.isSymbolic())
				int idx = cur.find(refName);
				if (idx >= 0)
					cur = cur.set(idx
				else
					cur = cur.add(idx
			}

			commitPackedRefs(lck

			for (String refName : refs) {
				File refFile = fileFor(refName);
				if (!refFile.exists())
					continue;
				LockFile rLck = new LockFile(refFile
						parent.getFS());
				if (!rLck.lock())
					continue;
				try {
					LooseRef currentLooseRef = scanRef(null
					if (currentLooseRef == null || currentLooseRef.isSymbolic())
						continue;
					Ref packedRef = cur.get(refName);
					ObjectId clr_oid = currentLooseRef.getObjectId();
					if (clr_oid != null
							&& clr_oid.equals(packedRef.getObjectId())) {
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
					}
				} finally {
					rLck.unlock();
				}
			}
		} finally {
			lck.unlock();
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

