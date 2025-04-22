	private static boolean isImperativeExpressionVisible(MExpression exp, final ExpressionContext eContext) {
		MImperativeExpression imperativeExpression = (MImperativeExpression) exp;
		Object imperativeExpressionObject = imperativeExpression.getObject();
		if (imperativeExpressionObject == null) {
			IContributionFactory contributionFactory = eContext.eclipseContext.get(IContributionFactory.class);
			Object newImperativeExpression = contributionFactory.create(imperativeExpression.getContributionURI(),
					eContext.eclipseContext);
			imperativeExpression.setObject(newImperativeExpression);
			imperativeExpressionObject = newImperativeExpression;
		}
		Object result = ContextInjectionFactory.invoke(imperativeExpressionObject, Evaluate.class, eContext.eclipseContext, null,
				missingEvaluate);
		if (result == missingEvaluate) {
			throw new IllegalStateException(
					"There is no method annotated with @Evaluate in the imperative expression class"); //$NON-NLS-1$
		}
		return (boolean) result;
	}

