	/*
	 * We are obligated to return a non-null IHandlerActivation from our
	 * activate calls. It is only used as a token, but must not return null from
	 * certain methods. This token represents passing the MakeHandlerGo handler
	 * back into the system.
	 */
	private static IHandlerActivation getSystemHandlerActivation(IEclipseContext context, final String cmdId) {
		if (systemHandlerActivation == null) {
			final IWorkbench wb = context.get(IWorkbench.class);

			systemHandlerActivation = new IHandlerActivation() {

				public int compareTo(Object o) {
					return -1;
				}

				public void setResult(boolean result) {
				}

				public int getSourcePriority() {
					return 0;
				}
