		Collection<Ref> txnRefs;
		if (txnNamespace != null) {
			txnRefs = bootstrap.getRefs(txnNamespace).values();
		} else {
			Ref r = bootstrap.exactRef(txnCommitted);
			if (r != null && r.getObjectId() != null) {
				txnRefs = Collections.singleton(r);
			} else {
				txnRefs = Collections.emptyList();
			}
		}

		List<Ref> otherRefs = bootstrap.getAdditionalRefs();
		List<Ref> all = new ArrayList<>(txnRefs.size() + otherRefs.size());
		all.addAll(txnRefs);
		all.addAll(otherRefs);
		return all;
