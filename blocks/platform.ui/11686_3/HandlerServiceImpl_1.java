	public final static String STATIC_CONTEXT = "HandlerServiceImpl.staticContext"; //$NON-NLS-1$
	public final static String HANDLER_EXCEPTION = "HandlerServiceImpl.exception"; //$NON-NLS-1$

	private static LinkedList<ExecutionContexts> contextStack = new LinkedList<ExecutionContexts>();

	public static IHandler getHandler(String commandId) {
		return new HandlerServiceHandler(commandId);
	}

	static class ExecutionContexts {
		public IEclipseContext context;
		public IEclipseContext staticContext;

		public ExecutionContexts(IEclipseContext ctx, IEclipseContext staticCtx) {
			context = ctx;
			staticContext = staticCtx;
		}
	}

	static LinkedList<ExecutionContexts> getContextStack() {
		return contextStack;
	}

	public static void push(IEclipseContext ctx, IEclipseContext staticCtx) {
		getContextStack().addFirst(new ExecutionContexts(ctx, staticCtx));
	}

	static ExecutionContexts pop() {
		return getContextStack().poll();
	}

	static ExecutionContexts peek() {
		return getContextStack().peek();
	}
