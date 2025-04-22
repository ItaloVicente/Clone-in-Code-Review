	private static boolean isImperativeExpressionVisible(MImperativeExpression exp, final ExpressionContext eContext) {
		Object imperativeExpressionObject = exp.getObject();
		if (imperativeExpressionObject == null) {
			IContributionFactory contributionFactory = eContext.eclipseContext.get(IContributionFactory.class);
			Object newImperativeExpression = contributionFactory.create(exp.getContributionURI(),
					eContext.eclipseContext);
			exp.setObject(newImperativeExpression);
			imperativeExpressionObject = newImperativeExpression;
		}

		Object result = null;

		if (exp.isTracking()) {
			result = invoke(imperativeExpressionObject, Evaluate.class, eContext.eclipseContext, null, missingEvaluate);
		} else {
			result = ContextInjectionFactory.invoke(imperativeExpressionObject, Evaluate.class, eContext.eclipseContext,
					null, missingEvaluate);
		}

		if (result == missingEvaluate) {
			throw new IllegalStateException(
					"There is no method annotated with @Evaluate in the imperative expression class"); //$NON-NLS-1$
		}
		return (boolean) result;
	}

	final private static InjectorImpl injector = (InjectorImpl) InjectorFactory.getDefault();

	static private Object invoke(Object object, Class<? extends Annotation> qualifier, IEclipseContext context,
			IEclipseContext localContext, Object defaultValue) throws InjectionException {
		PrimaryObjectSupplier supplier = ContextObjectSupplier.getObjectSupplier(context, injector);
		PrimaryObjectSupplier tempSupplier = ContextObjectSupplier.getObjectSupplier(localContext, injector);
		return injector.invoke(object, qualifier, defaultValue, supplier, tempSupplier, false, true);
	}

