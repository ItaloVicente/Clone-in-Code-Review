	public Map<AnyObjectId, Set<Ref>> getAllRefsByPeeledObjectId() {
		Map<String, Ref> allRefs = getAllRefs();
		Map<AnyObjectId, Set<Ref>> ret = new HashMap<AnyObjectId, Set<Ref>>(allRefs.size());
		for (Ref ref : allRefs.values()) {
			ref = peel(ref);
			AnyObjectId target = ref.getPeeledObjectId();
			if (target == null)
				target = ref.getObjectId();
			Set<Ref> oset = ret.put(target, Collections.singleton(ref));
			if (oset != null) {
				if (oset.size() == 1) {
					oset = new HashSet<Ref>(oset);
				}
				ret.put(target, oset);
				oset.add(ref);
			}
		}
		return ret;
	}
