				final SupportedAlgorithm diffAlg = getRepository().getConfig().getEnum(ConfigConstants.CONFIG_DIFF_SECTION
				final MergeAlgorithm mergeAlgorithm = new MergeAlgorithm(DiffAlgorithm.getAlgorithm(diffAlg));
				final RawText baseText = base == null ? RawText.EMPTY_TEXT
						: new RawText(getRepository().open(
								base.getEntryObjectId()
								.getCachedBytes());
				final RawText oursText = ours == null ? RawText.EMPTY_TEXT
						: new RawText(getRepository().open(
								ours.getEntryObjectId()
								.getCachedBytes());
				final RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
						: new RawText(getRepository().open(
								theirs.getEntryObjectId()
								.getCachedBytes());
				final MergeResult<RawText> mergeResult = mergeAlgorithm.merge(
						RawTextComparator.DEFAULT
						theirsText);
				mergeResults.put(tw.getPathString()
