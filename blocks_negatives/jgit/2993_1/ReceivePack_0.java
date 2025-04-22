		refs = refFilter.filter(db.getAllRefs());
		final Ref head = refs.remove(Constants.HEAD);
		adv.send(refs);
		if (head != null && !head.isSymbolic())
			adv.advertiseHave(head.getObjectId());
		adv.includeAdditionalHaves(db);
