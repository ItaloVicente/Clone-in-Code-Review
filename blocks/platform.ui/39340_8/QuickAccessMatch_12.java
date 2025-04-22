package org.eclipse.ui.quickaccess;

public class QuickAccessMatch {
	public IQuickAccessElement element;
	public IQuickAccessProvider provider;
	public int[][] elementMatchRegions;
	public int[][] providerMatchRegions;

	private int matchQuality;

	public static final int[][] EMPTY_MATCH_REGION = new int[0][0];

	public static final int MATCH_PERFECT = 0;

	public static final int MATCH_EXCELLENT = 5;

	public static final int MATCH_GOOD = 10;

	public static final int MATCH_PARTIAL = 15;

	public QuickAccessMatch(IQuickAccessElement element, IQuickAccessProvider provider,
			int[][] elementMatchRegions, int[][] providerMatchRegions, int matchQuality) {
		this.element = element;
		this.provider = provider;
		this.elementMatchRegions = elementMatchRegions;
		this.providerMatchRegions = providerMatchRegions;
		this.matchQuality = matchQuality;
	}

	public int getMatchQuality() {
		return matchQuality;
	}

}
