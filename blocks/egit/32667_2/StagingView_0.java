		RepositoryUtil repositoryUtil = Activator.getDefault()
				.getRepositoryUtil();

		StringBuilder sb = new StringBuilder();
		sb.append(repositoryUtil.getRepositoryName(repository));

		appendRepositoryDecoration(sb, repository, repositoryUtil);

		return sb.toString();
	}

	private static StringBuilder appendRepositoryDecoration(StringBuilder sb,
			Repository repository, RepositoryUtil repositoryUtil) {

		try {
			String branchName = repository.getBranch();

			String statusString = null;

			BranchTrackingStatus trackingStatus = BranchTrackingStatus.of(
					repository, branchName);
			if (trackingStatus != null)
				statusString = GitLabelProvider
						.formatBranchTrackingStatus(trackingStatus);

			String shortBranchName = repositoryUtil.getShortBranch(repository);

			sb.append(' ');
			sb.append('[');
			sb.append(shortBranchName);

			if (statusString != null && statusString.length() > 0) {
				sb.append(' ');
				sb.append(statusString);
			}

			RepositoryState state = repository.getRepositoryState();
			if (state != RepositoryState.SAFE) {
				sb.append(" - "); //$NON-NLS-1$
				sb.append(state.getDescription());
			}

			sb.append(']');
		} catch (IOException e) {
		}
		return sb;
