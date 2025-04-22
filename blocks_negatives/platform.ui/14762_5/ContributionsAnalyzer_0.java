		try {
			ExpressionInfo info = ref.computeExpressionInfo();
			String[] names = info.getAccessedPropertyNames();
			for (String name : names) {
			}
			return ref.evaluate(eContext) != EvaluationResult.FALSE;
		} catch (CoreException e) {
			trace("isVisible exception", e); //$NON-NLS-1$
