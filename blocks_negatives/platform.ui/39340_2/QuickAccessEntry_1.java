	/**
	 * Provides a rough indicator of how good of a match this entry was to its
	 * filter. Lower values indicate better match quality. A value of 0
	 * indicates the filter string was an exact match to the label or that there
	 * is no filter being applied.
	 */
	private int matchQuality;

	/**
	 * Indicates the filter string was a perfect match to the label or there is
	 * no filter applied
	 * 
	 * @see #getMatchQuality()
	 */
	public static final int MATCH_PERFECT = 0;

	/**
	 * Indicates this entry is very relevant for the filter string. Recommended
	 * value for when the filter was found at the start of the element's label
	 * or a complete case sensitive camel case match.
	 * 
	 * @see #getMatchQuality()
	 */
	public static final int MATCH_EXCELLENT = 5;

	/**
	 * Indicates this entry is relevant for the filter string. Recommended value
	 * for when the complete filter was found somewhere inside the element's
	 * label or provider.
	 * 
	 * @see #getMatchQuality()
	 */
	public static final int MATCH_GOOD = 10;

	/**
	 * Indicates only part of the filter string matches to the element's label.
	 * 
	 * @see #getMatchQuality()
	 */
	public static final int MATCH_PARTIAL = 15;

	/**
	 * Creates a new quick access entry from the given element and provider. If
	 * no filter was used to match this entry the element/provider match regions
	 * may be empty and the match quality should be {@link #MATCH_PERFECT}
	 * 
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
	 */
	QuickAccessEntry(QuickAccessElement element, QuickAccessProvider provider,
			int[][] elementMatchRegions, int[][] providerMatchRegions, int matchQuality) {
		this.element = element;
		this.provider = provider;
		this.elementMatchRegions = elementMatchRegions;
		this.providerMatchRegions = providerMatchRegions;
		this.matchQuality = matchQuality;
