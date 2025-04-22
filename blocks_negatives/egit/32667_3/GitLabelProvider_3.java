	private String getSimpleTextFor(RefNode refNode) {
		return refNode.getObject().getName();
	}

	/**
	 * @param repository
	 * @return simple text for repository
	 */
	public static String getSimpleTextFor(Repository repository) {
		String name = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		File directory = repository.getDirectory();
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(directory.getAbsolutePath());
		return sb.toString();
	}

