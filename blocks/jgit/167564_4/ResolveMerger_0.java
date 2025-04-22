	private MergeResult<SubmoduleConflict> createGitLinksMergeResult(
			CanonicalTreeParser base
			CanonicalTreeParser theirs) {
		return new MergeResult<>(Arrays.asList(
				new SubmoduleConflict(
						base == null ? null : base.getEntryObjectId())
				new SubmoduleConflict(
						ours == null ? null : ours.getEntryObjectId())
				new SubmoduleConflict(
						theirs == null ? null : theirs.getEntryObjectId())));
	}

