	public static void deconfigure(final IProject p) throws IOException {
		trace("deconfigure(" + p.getName() + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		GitProjectData d = lookup(p);
		if (d == null) {
			deletePropertyFiles(p);
		} else {
			d.deletePropertyFilesAndUncache();
			unmap(d);
		}
	}

