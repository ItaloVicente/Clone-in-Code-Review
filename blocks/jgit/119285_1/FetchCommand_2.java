	private List<RefSpec> applyOptions(List<RefSpec> refSpecs2) {
		if (!isForceUpdate()) {
			return refSpecs2;
		}
		List<RefSpec> updated = new ArrayList<>(3);
		for (RefSpec refSpec : refSpecs2) {
			updated.add(refSpec.setForceUpdate(true));
		}
		return updated;
	}

