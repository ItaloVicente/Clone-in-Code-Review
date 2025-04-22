	private EvaluationCache getEvaluationCache(Map<VisibilityAssistant, EvaluationCache> anEvaluationMap,
			VisibilityAssistant aVisibilityAssistant) {
		EvaluationCache c = anEvaluationMap.get(aVisibilityAssistant);
		if (c == null) {
			anEvaluationMap.put(aVisibilityAssistant, c = new EvaluationCache(aVisibilityAssistant));
		}
		return c;
	}

