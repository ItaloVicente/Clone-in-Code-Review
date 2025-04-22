		if (refs == null) {
			refs = refFilter.filter(db.getAllRefs());

			Ref head = refs.get(Constants.HEAD);
			if (head != null && head.isSymbolic())
				refs.remove(Constants.HEAD);
