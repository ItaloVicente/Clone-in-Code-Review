	private EditList diff(Candidate parent
		RawText p = parent.sourceText;
		RawText s = source.sourceText;

		int start = source.regionList.sourceStart;
		int tail = s.size() - source.sourceEnd();
		if (start == 0 && tail == 0)
			return diffAlgorithm.diff(textComparator

		Subsequence<RawText> pSub = subseq(p
		Subsequence<RawText> sSub = subseq(s
		return Subsequence.toBase(diffAlgorithm.diff(
				new SubsequenceComparator<RawText>(textComparator)
				pSub
	}

	private static final Subsequence<RawText> subseq(
			RawText s
		return new Subsequence<RawText>(s
	}

