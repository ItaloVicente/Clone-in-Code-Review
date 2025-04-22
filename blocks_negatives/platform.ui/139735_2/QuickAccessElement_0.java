
	private String wsFilter;
	private Pattern wsPattern;

	/**
	 * Get the existing {@link Pattern} for the given filter, or create a new
	 * one. The generated pattern will replace whitespaces with * to match all.
	 *
	 * @param filter
	 * @return
	 */
	private Pattern getWhitespacesPattern(String filter) {
		if (wsPattern == null || !filter.equals(wsFilter)) {
			wsFilter = filter;
			String sFilter = filter.replaceFirst(WS_START, EMPTY_STR).replaceFirst(WS_END, EMPTY_STR)
					.replaceAll(PAR_START, ONE_CHAR).replaceAll(PAR_END, ONE_CHAR);
			sFilter = String.format(".*(%s).*", sFilter.replaceAll(ANY_WS, ").*(")); //$NON-NLS-1$//$NON-NLS-2$
			wsPattern = safeCompile(sFilter);
		}
		return wsPattern;
	}

	private String wcFilter;
	private Pattern wcPattern;

	/**
	 * Get the existing {@link Pattern} for the given filter, or create a new
	 * one. The generated pattern will handle '*' and '?' wildcards.
	 *
	 * @param filter
	 * @return
	 */
	private Pattern getWildcardsPattern(String filter) {
		if (wcPattern == null || !filter.equals(wcFilter)) {
			wcFilter = filter;
			String sFilter = filter.replaceFirst(WS_START, EMPTY_STR).replaceFirst(WS_END, EMPTY_STR)
					.replaceAll(PAR_START, ONE_CHAR).replaceAll(PAR_END, ONE_CHAR);
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<sFilter.length(); i++) {
				char c = sFilter.charAt(i);
				if(c=='*'||c=='?') {
				} else {
					sb.append(c);
				}
			}
			sFilter = String.format(".*(%s).*", sb.toString()); //$NON-NLS-1$
			wcPattern = safeCompile(sFilter);
		}
		return wcPattern;
	}

	/**
	 * A safe way to compile some unknown pattern, avoids possible
	 * {@link PatternSyntaxException}. If the pattern can't be compiled, some
	 * not matching pattern will be returned.
	 *
	 * @param pattern
	 *            some pattern to compile, not null
	 * @return a {@link Pattern} object compiled from given input or a dummy
	 *         pattern which do not match anything
	 */
	private Pattern safeCompile(String pattern) {
		try {
			return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		} catch (Exception e) {
		}
	}

	int i = 0;
	/**
	 * If this element is a match (partial, complete, camel case, etc) to the
	 * given filter, returns a {@link QuickAccessEntry}. Otherwise returns
	 * <code>null</code>;
	 *
	 * @param filter
	 *            filter for matching
	 * @param providerForMatching
	 *            the provider that will own the entry
	 * @return a quick access entry or <code>null</code>
	 */
	public QuickAccessEntry match(String filter,
			QuickAccessProvider providerForMatching) {
		String matchLabel = getMatchLabel();
		int index = matchLabel.toLowerCase().indexOf(filter);
		if (index != -1) {
			index = getLabel().toLowerCase().indexOf(filter);
				int quality = matchLabel.toLowerCase().equals(filter) ? QuickAccessEntry.MATCH_PERFECT
						: (matchLabel.toLowerCase().startsWith(filter) ? QuickAccessEntry.MATCH_EXCELLENT
								: QuickAccessEntry.MATCH_GOOD);
				return new QuickAccessEntry(this, providerForMatching,
						new int[][] { { index, index + filter.length() - 1 } },
	 EMPTY_INDICES, quality);
			}
			return new QuickAccessEntry(this, providerForMatching, EMPTY_INDICES, EMPTY_INDICES, QuickAccessEntry.MATCH_PARTIAL);
		}
		Pattern p;
			p = getWildcardsPattern(filter);
		} else {
			p = getWhitespacesPattern(filter);
		}
		Matcher m = p.matcher(matchLabel);
		if (m.matches()) {
			String label = getLabel();
			if (!matchLabel.equals(label)) {
				m = p.matcher(getLabel());
				if (!m.matches()) {
					return new QuickAccessEntry(this, providerForMatching, EMPTY_INDICES, EMPTY_INDICES,
							QuickAccessEntry.MATCH_GOOD);
				}
			}
			int groupCount = m.groupCount();
			int[][] indices = new int[groupCount][];
			for (int i = 0; i < groupCount; i++) {
				int nGrp = i + 1;
				indices[i] = new int[] { m.start(nGrp), m.end(nGrp) - 1 };
			}
			int quality = QuickAccessEntry.MATCH_EXCELLENT;
			return new QuickAccessEntry(this, providerForMatching,
					indices,
					EMPTY_INDICES, quality );
		}
		index = combinedMatchLabel.toLowerCase().indexOf(filter);
			index = combinedLabel.toLowerCase().indexOf(filter);
				int lengthOfElementMatch = index + filter.length() - providerForMatching.getName().length() - 1;
				if (lengthOfElementMatch > 0) {
					return new QuickAccessEntry(this, providerForMatching,
							new int[][] { { 0, lengthOfElementMatch - 1 } },
							new int[][] { { index, index + filter.length() - 1 } }, QuickAccessEntry.MATCH_GOOD);
				}
				return new QuickAccessEntry(this, providerForMatching,
						EMPTY_INDICES, new int[][] { { index, index + filter.length() - 1 } },
						QuickAccessEntry.MATCH_GOOD);
			}
			return new QuickAccessEntry(this, providerForMatching,
					EMPTY_INDICES, EMPTY_INDICES, QuickAccessEntry.MATCH_PARTIAL);
		}
		index = camelCase.indexOf(filter);
		if (index != -1) {
			int[][] indices = CamelUtil.getCamelCaseIndices(matchLabel, index, filter
					.length());
			return new QuickAccessEntry(this, providerForMatching, indices,
 EMPTY_INDICES,
					QuickAccessEntry.MATCH_GOOD);
		}
		String combinedCamelCase = CamelUtil.getCamelCase(combinedLabel);
		index = combinedCamelCase.indexOf(filter);
		if (index != -1) {
			String providerCamelCase = CamelUtil.getCamelCase(providerForMatching
					.getName());
			int lengthOfElementMatch = index + filter.length()
					- providerCamelCase.length();
			if (lengthOfElementMatch > 0) {
				return new QuickAccessEntry(
						this,
						providerForMatching,
						CamelUtil.getCamelCaseIndices(matchLabel, 0, lengthOfElementMatch),
						CamelUtil.getCamelCaseIndices(providerForMatching.getName(),
 index,
								filter.length() - lengthOfElementMatch),
						QuickAccessEntry.MATCH_GOOD);
			}
			return new QuickAccessEntry(this, providerForMatching,
					EMPTY_INDICES, CamelUtil.getCamelCaseIndices(providerForMatching
.getName(), index,
							filter.length()), QuickAccessEntry.MATCH_GOOD);
		}
		return null;
