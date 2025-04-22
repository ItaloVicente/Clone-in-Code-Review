	private static boolean isJavaExpressionVisible(MExpression exp, final ExpressionContext eContext) {
		MJavaExpression javaExpression = (MJavaExpression) exp;
		Object javaExpressionObject = javaExpression.getObject();
		if (javaExpressionObject == null) {
			IContributionFactory contributionFactory = eContext.eclipseContext.get(IContributionFactory.class);
			Object newJavaExpression = contributionFactory.create(javaExpression.getContributionURI(),
					eContext.eclipseContext);
			javaExpression.setObject(newJavaExpression);
			javaExpressionObject = newJavaExpression;
		}
		Object result = ContextInjectionFactory.invoke(javaExpressionObject, Evaluate.class, eContext.eclipseContext, null,
				missingEvaluate);
		if (result == missingEvaluate) {
			throw new IllegalStateException(
					"There is no method annotated with @Evaluate in the java expression class"); //$NON-NLS-1$
		}
		return (boolean) result;
	}

