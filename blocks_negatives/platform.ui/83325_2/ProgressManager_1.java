	public void runInUI(final IRunnableContext context,
			final IRunnableWithProgress runnable, final ISchedulingRule rule)
			throws InvocationTargetException, InterruptedException {
		final RunnableWithStatus runnableWithStatus = new RunnableWithStatus(
				context,
				runnable, rule);
