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

