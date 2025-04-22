	protected static <T> T getFromContext(IEvaluationContext context, Class<T> expectedType) {
		if (context == null || expectedType == null) {
			throw new NullPointerException();
		}
		final Object rawValue = context.getVariable(expectedType.getName());
		return (expectedType.isInstance(rawValue)) ? expectedType.cast(rawValue) : null;
	}

