	public void addAdditionalRefs(final Repository repo) throws IOException {
		for (Ref ref : repo.getRefDatabase().getAdditionalRefs()) {
			Set<Ref> set = reverseRefMap.get(ref.getObjectId());
			if (set == null) {
				set = Collections.singleton(ref);
			} else {
				set = new HashSet<Ref>(set);
				set.add(ref);
			}
			reverseRefMap.put(ref.getObjectId()
		}
	}

