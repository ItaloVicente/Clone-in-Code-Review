	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (GitSynchronizeData data : gsd) {
			builder.append(data.getRepository().getWorkDir());
			builder.append(" "); //$NON-NLS-1$
		}

		return builder.toString();
	}

