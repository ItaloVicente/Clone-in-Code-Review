		this(d
	}

	public Repository(final File d
		this(d
	}

	public Repository(final File d
			final File[] alternateObjectDir

		if (workTree != null) {
			workDir = workTree;
			if (d == null)
				gitDir = new File(workTree
			else
				gitDir = d;
		} else {
			if (d != null)
				gitDir = d;
			else
				throw new IllegalArgumentException("Either GIT_DIR or GIT_WORK_TREE must be passed to Repository constructor");
		}
