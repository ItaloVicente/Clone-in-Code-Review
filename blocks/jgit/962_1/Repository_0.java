		String desc;
		if (getDirectory() != null)
			desc = getDirectory().getPath();
		else
			desc = getClass().getSimpleName() + "-"
					+ System.identityHashCode(this);
		return "Repository[" + desc + "]";
