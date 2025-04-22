		if (refs == null) {
			refs = refFilter.filter(db.getAllRefs());

			Ref head = refs.get(Constants.HEAD);
			if (head != null && head.isSymbolic())
				refs.remove(Constants.HEAD);

			for (Ref ref : refs.values()) {
				if (ref.getObjectId() != null)
					advertisedHaves.add(ref.getObjectId());
			}
			advertisedHaves.addAll(db.getAdditionalHaves());
		}
