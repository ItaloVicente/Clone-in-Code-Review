	public void setPathFilters(PathFilter... pathFilters) {
		if (pathFilters == null) {
			this.pathFilters = null;
		} else {
			this.pathFilters = new HashSet<>(pathFilters.length);
			for (PathFilter pathFilter : pathFilters) {
				this.pathFilters.add(pathFilter.getPath());
			}
		}
	}

