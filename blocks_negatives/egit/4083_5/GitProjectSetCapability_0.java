	private static final class ProjectReferenceComparator implements
			Comparator<ProjectReference>, Serializable {
		private static final long serialVersionUID = 1L;

		public int compare(ProjectReference o1, ProjectReference o2) {
			final boolean reposEqual = o1.repository.equals(o2.repository);
			final boolean branchesEqual = o1.branch
					.equals(o2.branch);
			final boolean projectDirsEqual = o1.projectDir
					.equals(o2.projectDir);
			return reposEqual && branchesEqual && projectDirsEqual ? 0 : 1;
		}
	}

	private static final class ProjectReference {

		private static final String DEFAULT_BRANCH = Constants.MASTER;

		/**
		 * a relative path (from the repository root) to a project
		 */
		String projectDir;

		/**
		 * <code>repository</code> parameter
		 */
		URIish repository;

		/**
		 * the remote branch that will be checked out, see <code>--branch</code>
		 * option
		 */
		String branch = DEFAULT_BRANCH;

		@SuppressWarnings("boxing")
		ProjectReference(final String reference) throws URISyntaxException, IllegalArgumentException {
			final String[] tokens = reference.split(Pattern.quote(SEPARATOR));
			if (tokens.length != 4)
				throw new IllegalArgumentException(NLS.bind(
						CoreText.GitProjectSetCapability_InvalidTokensCount, new Object[] {
								4, tokens.length, tokens }));

			this.repository = new URIish(tokens[1]);
				this.branch = tokens[2];
			this.projectDir = tokens[3];
		}
	}

