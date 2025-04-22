	 * Returns true if a link is present at the given character location
	 */
	public boolean isLinkAt(int offset) {
		Optional<HyperlinkRange> potentialMatch = linkRanges.stream().filter(r -> r.contains(offset)).findAny();
		return potentialMatch.isPresent();
	}

	/**
	 * Returns the link at the given offset (if there is one), otherwise returns
	 * <code>null</code>.
