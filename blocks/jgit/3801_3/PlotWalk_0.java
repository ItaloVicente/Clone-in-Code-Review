	public void addAdditionalRefs(Iterable<Ref> refs) throws IOException {
		for (Ref ref : refs) {
			Set<Ref> set = reverseRefMap.get(ref.getObjectId());
			if (set == null)
				set = Collections.singleton(ref);
			else {
				set = new HashSet<Ref>(set);
				set.add(ref);
			}
			reverseRefMap.put(ref.getObjectId()
		}
	}

