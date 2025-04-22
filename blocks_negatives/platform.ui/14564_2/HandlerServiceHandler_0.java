	IEclipseContext getStaticContext(Object evalObj) {
		if (evalObj instanceof ExecutionContexts) {
			return ((ExecutionContexts) evalObj).staticContext;
		}
		if (evalObj instanceof IEclipseContext) {
			return (IEclipseContext) ((IEclipseContext) evalObj)
					.get(HandlerServiceImpl.STATIC_CONTEXT);
		}
		if (evalObj instanceof ExpressionContext) {
			return (IEclipseContext) (((ExpressionContext) evalObj).eclipseContext)
					.get(HandlerServiceImpl.STATIC_CONTEXT);
		}
		if (evalObj instanceof IEvaluationContext) {
			return getStaticContext(((IEvaluationContext) evalObj).getParent());
		}
