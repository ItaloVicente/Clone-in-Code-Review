	/**
	 * Load the project paths associated with the given branch. These paths will
	 * be relative to the repository root.
	 *
	 * @param branch
	 * @return non-null but possibly empty array of projects
	 */
	public String[] getProjectPaths(final String branch) {
		String pref = getPreference(branch);
		String value = Activator.getDefault().getPreferenceStore()
				.getString(pref);
		if (value.length() == 0)
			return new String[0];
		XMLMemento memento;
		try {
			memento = XMLMemento.createReadRoot(new StringReader(value));
		} catch (WorkbenchException e) {
			Activator.logError("Error reading branch-project associations", e); //$NON-NLS-1$
			return new String[0];
		}
		IMemento[] children = memento.getChildren(KEY_PROJECT);
		if (children.length == 0)
			return new String[0];
		List<String> projects = new ArrayList<>(children.length);
		for (int i = 0; i < children.length; i++) {
			String path = children[i].getTextData();
			if (path != null && path.length() > 0)
				projects.add(path);
		}
		return projects.toArray(new String[0]);
