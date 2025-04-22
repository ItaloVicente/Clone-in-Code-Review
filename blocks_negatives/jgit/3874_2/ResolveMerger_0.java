				RawText baseText = base == null ? RawText.EMPTY_TEXT
						: getRawText(base.getEntryObjectId(), db);
				RawText ourText = ours == null ? RawText.EMPTY_TEXT
						: getRawText(ours.getEntryObjectId(), db);
				RawText theirsText = theirs == null ? RawText.EMPTY_TEXT
						: getRawText(theirs.getEntryObjectId(), db);
				MergeResult<RawText> result = mergeAlgorithm.merge(
						RawTextComparator.DEFAULT, baseText, ourText,
						theirsText);
