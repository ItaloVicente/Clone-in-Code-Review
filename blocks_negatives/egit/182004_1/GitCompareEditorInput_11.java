		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((baseVersion == null) ? 0 : baseVersion.hashCode());
		result = prime * result
				+ ((compareVersion == null) ? 0 : compareVersion.hashCode());
		result = prime
				* result
				+ ((repository == null) ? 0 : repository.getDirectory()
						.hashCode());
		result = prime * result + Arrays.hashCode(resources);
