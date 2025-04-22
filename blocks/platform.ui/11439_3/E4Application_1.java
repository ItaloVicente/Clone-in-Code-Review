	private boolean checkInstanceLocation(Location instanceLocation,
			Shell shell, IEclipseContext context) {

		if (instanceLocation == null
				&& Boolean.FALSE.equals(context.get(IWorkbench.PERSIST_STATE))) {
			return true;
		}

