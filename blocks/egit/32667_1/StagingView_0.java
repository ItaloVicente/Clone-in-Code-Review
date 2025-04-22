		RepositoryUtil repositoryUtil = Activator.getDefault()
				.getRepositoryUtil();

		StringBuilder sb = new StringBuilder();
		sb.append(repositoryUtil.getRepositoryName(repository));

		appendRepositoryDecoration(sb, repository, repositoryUtil);

