	private EvaluationCache getEvaluationCache(Map anEvaluationMap,
			VisibilityAssistant aVisibilityAssistant) {
		EvaluationCache c = (EvaluationCache) anEvaluationMap
				.get(aVisibilityAssistant);
		if (c == null) {
			anEvaluationMap.put(aVisibilityAssistant, c = new EvaluationCache(
					aVisibilityAssistant));
		}
		return c;

	}

