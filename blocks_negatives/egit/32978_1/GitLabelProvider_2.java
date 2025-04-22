		try {
			if (element instanceof Repository)
				return getStyledTextFor((Repository) element);

			if (element instanceof GitModelRepository)
				return getStyledTextFor(((GitModelRepository) element)
						.getRepository());

		} catch (IOException e) {
			Activator.logError(NLS.bind(
					UIText.GitLabelProvider_UnableToRetrieveLabel,
					element.toString()), e);
		}
		return new StyledString(getText(element));
	}

	/**
	 * @param repository
	 * @return a styled string for the repository
	 * @throws IOException
	 */
	public static StyledString getStyledTextFor(Repository repository)
			throws IOException {
		File directory = repository.getDirectory();

		RepositoryUtil repositoryUtil = Activator.getDefault()
				.getRepositoryUtil();

		StyledString string = new StyledString();
		string.append(repositoryUtil.getRepositoryName(repository));

		String branch = repositoryUtil
				.getShortBranch(repository);
		if (branch != null) {
			string.append(' ');
			string.append('[', StyledString.DECORATIONS_STYLER);
			string.append(branch, StyledString.DECORATIONS_STYLER);

			RepositoryState repositoryState = repository.getRepositoryState();
			if (repositoryState != RepositoryState.SAFE) {
				string.append(" - ", StyledString.DECORATIONS_STYLER); //$NON-NLS-1$
				string.append(repositoryState.getDescription(),
						StyledString.DECORATIONS_STYLER);
			}

			BranchTrackingStatus trackingStatus = BranchTrackingStatus.of(repository, branch);
			if (trackingStatus != null
					&& (trackingStatus.getAheadCount() != 0 || trackingStatus
							.getBehindCount() != 0)) {
				String formattedTrackingStatus = formatBranchTrackingStatus(trackingStatus);
				string.append(' ');
				string.append(formattedTrackingStatus, StyledString.DECORATIONS_STYLER);
			}
			string.append(']', StyledString.DECORATIONS_STYLER);
		}

		string.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
		string.append(directory.getAbsolutePath(), StyledString.QUALIFIER_STYLER);

		return string;
