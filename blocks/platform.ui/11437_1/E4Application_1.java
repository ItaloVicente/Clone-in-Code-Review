	private boolean checkInstanceLocation(Location instanceLocation,
			Shell shell, IEclipseContext context) {

		if (Boolean.FALSE.equals(context.get(IWorkbench.PERSIST_STATE))) {
			return true;
		}

