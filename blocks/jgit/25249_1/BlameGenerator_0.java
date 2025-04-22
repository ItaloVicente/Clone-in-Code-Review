		EditList editList;

		int start = source.regionList.sourceStart;
		int tail = source.sourceText.size() - source.sourceEnd();
		if (start == 0 && tail == 0)
			editList = diffAlgorithm.diff(textComparator
					parent.sourceText
		else {
			Subsequence<RawText> sa = new Subsequence<RawText>(
					parent.sourceText
					start
			Subsequence<RawText> sb = new Subsequence<RawText>(
					source.sourceText
					start
			editList = Subsequence.toBase(diffAlgorithm.diff(
					new SubsequenceComparator<RawText>(textComparator)
					sa
		}

