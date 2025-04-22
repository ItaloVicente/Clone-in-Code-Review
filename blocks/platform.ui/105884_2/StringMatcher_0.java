	protected String fPattern;

	protected int fLength; // pattern length

	protected boolean fIgnoreWildCards;

	protected boolean fIgnoreCase;

	protected boolean fHasLeadingStar;

	protected boolean fHasTrailingStar;

	protected String fSegments[]; // the given pattern is split into * separated segments

	protected int fBound = 0;

	protected String[] wordsSplitted;

	protected Word words[];

	protected static final char fSingleWildCard = '\u0000';

	public class Word {
		boolean hasTrailingStar = false;
		boolean hasLeadingStar = false;
		int bound = 0;
		String[] fragments = null;
		String pattern = null;
	}

	public static class Position {
		int start; // inclusive

		int end; // exclusive

		public Position(int start, int end) {
			this.start = start;
			this.end = end;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}
	}

	public StringMatcher(String pattern, boolean ignoreCase, boolean ignoreWildCards) {
		if (pattern == null) {
