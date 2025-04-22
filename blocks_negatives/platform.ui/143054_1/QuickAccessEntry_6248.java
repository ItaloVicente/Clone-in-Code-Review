	 * @param element
	 *            the element this entry will represent
	 * @param provider
	 *            the provider that owns this entry
	 * @param elementMatchRegions
	 *            list of text regions the filter string matched in the element
	 *            label, possibly empty
	 * @param providerMatchRegions
	 *            list of text regions the filter string matches in the provider
	 *            label, possible empty
	 * @param matchQuality
	 *            a rough indication of how closely the filter matched, lower
	 *            values indicate a better match. It is recommended to use the
	 *            constants available on this class: {@link #MATCH_PERFECT},
	 *            {@link #MATCH_EXCELLENT}, {@link #MATCH_GOOD},
	 *            {@link #MATCH_PARTIAL}
