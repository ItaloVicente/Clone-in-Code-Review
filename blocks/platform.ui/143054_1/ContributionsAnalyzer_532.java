		IEclipseContext staticContext = EclipseContextFactory.create("Evaluation-Static");//$NON-NLS-1$
		staticContext.set(MImperativeExpression.class, exp);
		try {
			if (exp.isTracking()) {
				result = invoke(imperativeExpressionObject, Evaluate.class, eContext.eclipseContext, staticContext,
						missingEvaluate);
			} else {
				result = ContextInjectionFactory.invoke(imperativeExpressionObject, Evaluate.class,
						eContext.eclipseContext, staticContext, missingEvaluate);
			}
		} finally {
			staticContext.dispose();
