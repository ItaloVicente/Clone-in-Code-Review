		void diffReplace(Edit r, long[] pCommon, int pIdx, int pEnd) {
			PatienceDiffIndex<S> p;
			Edit lcs;

			p = new PatienceDiffIndex<S>(cmp, a, b, r, pCommon, pIdx, pEnd);
			lcs = p.findLongestCommonSequence();

