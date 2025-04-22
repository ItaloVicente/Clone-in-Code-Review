
	/**
	 * @param event
	 *            the {@link ExecutionEvent}
	 * @return a {@link Repository} if all elements in the current selection map
	 *         to the same {@link Repository}, otherwise null
	 */
	public static Repository getRepository(ExecutionEvent event) {
		Object ctx = event.getApplicationContext();
		if (ctx instanceof IEvaluationContext)
			return SelectionUtils.getRepository((IEvaluationContext) ctx);
		return null;
	}

