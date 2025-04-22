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

		Object result = invoke(imperativeExpressionObject, Evaluate.class, eContext.eclipseContext, null,
				missingEvaluate);
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
		return injector.invoke(object, qualifier, defaultValue, supplier, tempSupplier, true);
	}

	public static void addMenuContributions(final MMenu menuModel, final ArrayList<MMenuContribution> toContribute,
