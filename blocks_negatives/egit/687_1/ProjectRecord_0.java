	/**
	 * @param parent
	 *            The parent folder of the .project file
	 * @param level
	 *            The number of levels deep in the provider the file is
	 */
	ProjectRecord(Object parent, int level) {
		this.parent = parent;
		this.level = level;
		setProjectName();
	}

