	public StatusCommand addPath(String path) {
		if (paths == null)
			paths = new LinkedList<String>();
		paths.add(path);
		return this;
	}

	public List<String> getPaths() {
		return paths;
	}

