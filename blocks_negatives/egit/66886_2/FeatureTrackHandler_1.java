		AbstractGitFlowBranchSelectionDialog<Ref> dialog = new AbstractGitFlowBranchSelectionDialog<Ref>(
				activeShell, refs,
				UIText.FeatureTrackHandler_selectFeature,
				UIText.FeatureTrackHandler_remoteFeatures) {
			@Override
			protected String getPrefix() {
				return R_REMOTES + DEFAULT_REMOTE_NAME + SEP
						+ gfRepo.getConfig().getFeaturePrefix();
			}
		};
