	private String describe(IEclipseContext context) {
		StringBuilder sb = new StringBuilder("\n\tcontext chain: "); //$NON-NLS-1$
		IEclipseContext activeContext = context;
		IEclipseContext child = context.getActiveChild();
		while (child != null) {
			sb.append(activeContext).append(" -> "); //$NON-NLS-1$
			activeContext = child;
			child = child.getActiveChild();
		}
		sb.append(activeContext);
		return sb.toString();
	}

