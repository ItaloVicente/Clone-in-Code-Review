	public boolean isLinkAt(int offset) {
		for (int[] linkRange : linkRanges) {
			if (offset >= linkRange[0] && offset < linkRange[0] + linkRange[1]) {
				return true;
			}
		}
		return false;
	}
