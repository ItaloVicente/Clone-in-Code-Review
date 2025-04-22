		synchronized (prefs) {
			Set<String> configuredStrings = new HashSet<String>();

			String dirs = prefs.get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
			if (dirs != null && dirs.length() > 0) {
				StringTokenizer tok = new StringTokenizer(dirs,
						File.pathSeparator);
				while (tok.hasMoreTokens()) {
					String dirName = tok.nextToken();
					configuredStrings.add(dirName);
				}
			}
			List<String> result = new ArrayList<String>();
			result.addAll(configuredStrings);
			Collections.sort(result);
			return result;
		}
