		IEclipsePreferences prefs = Activator.getDefault().getRepositoryUtil()
				.getPreferences();
		synchronized (prefs) {
			prefs.put(RepositoryUtil.PREFS_DIRECTORIES, "");
			prefs.flush();
		}
