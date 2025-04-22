	/**
	 * Track given ref, referencing a feature branch.
	 *
	 * @param repository
	 * @param ref
	 * @deprecated Use
	 *             {@link FeatureTrackOperation#FeatureTrackOperation(GitFlowRepository, Ref, int)}
	 *             instead.
	 */
	@Deprecated
	public FeatureTrackOperation(GitFlowRepository repository, Ref ref) {
		this(repository, ref, 0);
	}

