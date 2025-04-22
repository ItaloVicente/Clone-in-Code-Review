		boolean ret = false;
		try {
			ret = ref.evaluate(eContext) != EvaluationResult.FALSE;
		} catch (Exception e) {
			trace("isVisible exception", e); //$NON-NLS-1$
		}
		return ret;
