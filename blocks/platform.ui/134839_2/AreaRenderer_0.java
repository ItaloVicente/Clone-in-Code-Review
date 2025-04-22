	protected List<MPartStack> findDirectStacks(MPartSashContainer root) {
		List<MPartStack> result = new ArrayList<>();
		for (MPartSashContainerElement e : root.getChildren()) {
			if (e instanceof MPartStack) {
				result.add((MPartStack) e);
			} else if (e instanceof MPartSashContainer) {
				result.addAll(findDirectStacks((MPartSashContainer) e));
			}
		}
		return result;
	}

