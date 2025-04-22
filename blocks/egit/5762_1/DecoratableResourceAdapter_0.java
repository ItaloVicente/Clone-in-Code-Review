		for (String entry : collection) {
			String entryPath;
			if (entry.endsWith("/")) //$NON-NLS-1$
				entryPath = entry;
			else
				entryPath = entry + "/"; //$NON-NLS-1$
			if (path.startsWith(entryPath))
