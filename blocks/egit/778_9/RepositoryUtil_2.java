	public IEclipsePreferences getPreferences() {
		return prefs;
	}

	public void checkDirectories() {
		getConfiguredRepositories(true);
	}

	public List<String> getConfiguredRepositories() {
		return this.getConfiguredRepositories(false);
	}

	private List<String> getConfiguredRepositories(boolean checkPaths) {
		synchronized (prefs) {
			Set<String> configuredStrings = new HashSet<String>();
			Set<String> resultStrings = new HashSet<String>();

			String dirs = prefs.get(PREFS_DIRECTORIES, ""); //$NON-NLS-1$
			if (dirs != null && dirs.length() > 0) {
				StringTokenizer tok = new StringTokenizer(dirs,
						File.pathSeparator);
				while (tok.hasMoreTokens()) {
					String dirName = tok.nextToken();
					configuredStrings.add(dirName);
					if (checkPaths) {
						File testFile = new File(dirName);
						if (!FileKey.isGitRepository(testFile, FS.DETECTED)) {
							resultStrings.add(dirName);
						}
					}
					resultStrings.add(dirName);
				}
			}

			if (checkPaths && resultStrings.size() < configuredStrings.size()) {
				saveDirs(resultStrings);
			}
			List<String> result = new ArrayList<String>();
			result.addAll(resultStrings);
			Collections.sort(result);
			return result;
		}
	}

	public boolean addConfiguredRepository(File repositoryDir)
			throws IllegalArgumentException {
		synchronized (prefs) {

			if (!FileKey.isGitRepository(repositoryDir, FS.DETECTED)) {
				throw new IllegalArgumentException();
			}

			String dirString;
			try {
				dirString = repositoryDir.getCanonicalPath();
			} catch (IOException e) {
				dirString = repositoryDir.getAbsolutePath();
			}

			List<String> dirStrings = getConfiguredRepositories(false);
			if (dirStrings.contains(dirString)) {
				return false;
			} else {
				Set<String> dirs = new HashSet<String>();
				dirs.addAll(dirStrings);
				dirs.add(dirString);
				saveDirs(dirs);
				return true;
			}
		}
	}

	public boolean removeDir(File file) {
		synchronized (prefs) {

			String dir;
			try {
				dir = file.getCanonicalPath();
			} catch (IOException e1) {
				dir = file.getAbsolutePath();
			}

			Set<String> dirStrings = new HashSet<String>();
			dirStrings.addAll(getConfiguredRepositories(false));
			if (dirStrings.remove(dir)) {
				saveDirs(dirStrings);
				return true;
			}
			return false;
		}
	}

	private void saveDirs(Set<String> gitDirStrings) {
		StringBuilder sb = new StringBuilder();
		for (String gitDirString : gitDirStrings) {
			sb.append(gitDirString);
			sb.append(File.pathSeparatorChar);
		}

		prefs.put(PREFS_DIRECTORIES, sb.toString());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			IStatus error = new Status(IStatus.ERROR, Activator.getPluginId(),
					e.getMessage(), e);
			Activator.getDefault().getLog().log(error);
		}
	}

