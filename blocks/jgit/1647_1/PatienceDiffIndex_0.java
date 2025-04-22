		while (lcs != null && (recs[lcs.rIdx] & DUPLICATE) != 0)
			lcs = lcs.prior;
		if (lcs != null)
			lcs.prior = null;
		return lcs;
	}
