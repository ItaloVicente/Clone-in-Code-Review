		if (ref != null && ref.getSnapshot().equals(snapshot)
				&& !ref.isSymbolic() && id.equals(ref.getObjectId())) {
			ref.getSnapshot().setClean(snapshot);
			return ref;
		}
		return new LooseUnpeeled(snapshot
