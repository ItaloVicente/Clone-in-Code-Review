
				RawText baseText = base == null ? RawText.EMPTY_TEXT
						: getRawText(base.getEntryObjectId()
				RawText ourText = ours == null ? RawText.EMPTY_TEXT
						: getRawText(ours.getEntryObjectId()
				RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
						: getRawText(theirs.getEntryObjectId()
				MergeResult<RawText> result = mergeAlgorithm.merge(
						RawTextComparator.DEFAULT
						theirsText);
				mergeResults.put(tw.getPathString()
