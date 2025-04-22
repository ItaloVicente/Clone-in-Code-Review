	private int[] computeHashes(RawTextComparator cmp) {
		final int[] r = new int[lines.size()];
		for (int lno = 1; lno < lines.size() - 1; lno++) {
			final int ptr = lines.get(lno);
			final int end = lines.get(lno + 1);
			r[lno] = cmp.hashRegion(content, ptr, end);
		}
		return r;
	}

