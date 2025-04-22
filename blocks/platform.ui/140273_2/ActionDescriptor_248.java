		if (tpath != null) {
			int loc = tpath.lastIndexOf('/');
			if (loc != -1) {
				tgroup = tpath.substring(loc + 1);
				tpath = tpath.substring(0, loc);
			} else {
				tgroup = tpath;
				tpath = null;
			}
		}
		menuPath = mpath;
		menuGroup = mgroup;
		if ((tpath != null) && tpath.equals("Normal")) { //$NON-NLS-1$
