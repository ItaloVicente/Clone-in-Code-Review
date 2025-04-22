		SupportedAlgorithm diffAlg = local.getConfig().getEnum(
				ConfigConstants.CONFIG_DIFF_SECTION, null,
				ConfigConstants.CONFIG_KEY_ALGORITHM,
				SupportedAlgorithm.HISTOGRAM);
		mergeAlgorithm = new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
		commitNames = new String[] { "BASE", "OURS", "THEIRS" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
