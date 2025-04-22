	public final static String STATIC_CONTEXT = "HandlerServiceImpl.staticContext"; //$NON-NLS-1$

	private static LinkedList<IEclipseContext> contextStack = new LinkedList<IEclipseContext>();

	public static IHandler getHandler(String commandId) {
		return new HandlerServiceHandler(commandId);
	}

	static LinkedList<IEclipseContext> getContextStack() {
		return contextStack;
	}

	public static void push(IEclipseContext ctx) {
		getContextStack().addFirst(ctx);
	}

	static IEclipseContext pop() {
		return getContextStack().poll();
	}

	static IEclipseContext peek() {
		return getContextStack().peek();
	}
