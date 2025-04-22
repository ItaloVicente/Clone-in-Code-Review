	private void markStartAllRefs(Set<Ref> refs)
			throws IOException, IncorrectObjectTypeException {
		for (Ref ref : refs) {
			markStartRef(ref);
		}
	}

