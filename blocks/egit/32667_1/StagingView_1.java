		if (state != RepositoryState.SAFE) {
			sb.append('|');
			sb.append(state.getDescription());
		}

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

			sb.append(']');
		} catch (IOException e) {
		}
		return sb;
