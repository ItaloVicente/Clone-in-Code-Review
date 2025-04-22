		IEclipsePreferences prefs = getPrefs();
		prefs.put(PREFS_DIRECTORIES, sb.toString());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			IStatus error = new Status(IStatus.ERROR, Activator.getPluginId(),
					e.getMessage(), e);
			Activator.getDefault().getLog().log(error);
		}
	}
