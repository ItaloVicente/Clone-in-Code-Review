		FeatureBranchSelectionDialog dialog = new FeatureBranchSelectionDialog(
				HandlerUtil.getActiveShell(event), refs,
				UIText.FeatureCheckoutHandler_selectFeature,
				UIText.FeatureTrackHandler_remoteFeatures,
				R_REMOTES + DEFAULT_REMOTE_NAME + SEP + gfRepo.getConfig().getFeaturePrefix());

