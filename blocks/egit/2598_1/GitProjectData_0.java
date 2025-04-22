	public static void add(final IProject p, final GitProjectData d) {
		trace("add(" + p.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$

		cache(p, d);
	}

