	/**
	 * Instantiate a new {@link QuickAccessEntry} to search the given text in the
	 * eclipse help
	 *
	 * @param text String to search in the Eclipse Help
	 *
	 * @return the {@link QuickAccessEntry} to perform the action
	 */
	public QuickAccessEntry makeHelpSearchEntry(String text) {
		if (text.length() >= MIN_SEARCH_LENGTH) {
			QuickAccessElement searchHelpElement = getElements(text, null)[0];
			return new QuickAccessEntry(searchHelpElement, new HelpSearchProvider(), new int[][] {},
					new int[][] {}, QuickAccessEntry.MATCH_PERFECT);
		}
		return null;
	}
