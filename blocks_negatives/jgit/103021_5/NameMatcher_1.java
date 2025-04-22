	private int getFirstNotSlash(String s, int start) {
		int slashIdx = s.indexOf(slash, start);
		return slashIdx == start ? start + 1 : start;
	}

	private int getFirstSlash(String s, int start) {
		int slashIdx = s.indexOf(slash, start);
		return slashIdx == -1 ? s.length() : slashIdx;
	}

