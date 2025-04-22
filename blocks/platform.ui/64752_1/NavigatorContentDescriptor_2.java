	public boolean canCacheTriggerPoint() {
		return canCacheEnablement;
	}

	public boolean canCachePossibleChildren() {
		return canCachePossibleChildren;
	}

	private boolean checkCacheableStatus(Expression expression) {
		boolean canSafelyCacheExpressionResult = true;

		if (expression != null) {
			canSafelyCacheExpressionResult = false;
			ExpressionInfo info = expression.computeExpressionInfo();
			if (info != null) {
				String[] accessedPropertyNames = info.getAccessedPropertyNames();
				canSafelyCacheExpressionResult = accessedPropertyNames == null || accessedPropertyNames.length == 0
						|| info.getMisbehavingExpressionTypes() != null;
			}
		}
		return canSafelyCacheExpressionResult;
	}

