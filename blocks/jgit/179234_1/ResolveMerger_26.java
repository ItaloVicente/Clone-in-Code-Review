			Attributes attributes
			throws BinaryBlobException
		RawText baseText = base == null ? RawText.EMPTY_TEXT
				: getRawText(base.getEntryObjectId()
		RawText ourText = ours == null ? RawText.EMPTY_TEXT
				: getRawText(ours.getEntryObjectId()
		RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
				: getRawText(theirs.getEntryObjectId()
		mergeAlgorithm.setContentMergeStrategy(strategy);
		return mergeAlgorithm.merge(RawTextComparator.DEFAULT
				ourText
