		this.refDb = refdb;
		this.lock = lock;
		this.repository = repository;
	}

	private static List<Ref> toNewRefs(RevWalk rw
			throws IOException {
		List<Ref> refs = new ArrayList<>(pending.size());
		for (ReceiveCommand cmd : pending) {
			String name = cmd.getRefName();
			ObjectId newId = cmd.getNewId();
			String newSymref = cmd.getNewSymref();
			if (AnyObjectId.isEqual(ObjectId.zeroId()
					&& newSymref == null) {
				refs.add(new ObjectIdRef.Unpeeled(NEW
				continue;
			} else if (newSymref != null) {
				refs.add(new SymbolicRef(name
						new ObjectIdRef.Unpeeled(NEW
				continue;
			}

			RevObject obj = rw.parseAny(newId);
			RevObject peel = null;
			if (obj instanceof RevTag) {
				peel = rw.peel(obj);
			}
			if (peel != null) {
				refs.add(new ObjectIdRef.PeeledTag(PACKED
						peel.copy()));
			} else {
				refs.add(new ObjectIdRef.PeeledNonTag(PACKED
			}
		}
		return refs;
