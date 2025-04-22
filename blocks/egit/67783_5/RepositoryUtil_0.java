			dirString = prefs.get(PREFS_DIRECTORIES_REL, ""); //$NON-NLS-1$
			if (dirString.equals("")) { //$NON-NLS-1$
				dirs = migrateAbolutePaths();
			} else {
				dirs = toDirSet(dirString);
			}
