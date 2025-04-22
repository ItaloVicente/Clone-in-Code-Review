	private boolean canStoreInEvaluationCache(Set<NavigatorContentDescriptor> descriptors, boolean possibleChild) {
		boolean storeInEvaluationCache = true;
		for (NavigatorContentDescriptor descriptor : descriptors) {
			if (possibleChild ? descriptor.canCachePossibleChildren() : descriptor.canCacheTriggerPoint()) {
				storeInEvaluationCache = false;
				break;
			}
		}
		return storeInEvaluationCache;
	}

