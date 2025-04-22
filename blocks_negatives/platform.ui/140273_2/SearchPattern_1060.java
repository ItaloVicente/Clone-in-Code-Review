	 * Creates a search pattern with the rule to apply for matching index keys.
	 * It can be exact match, prefix match, pattern match or camelCase match.
	 * Rule can also be combined with a case sensitivity flag.
	 *
	 * @param allowedRules
	 *            one of {@link #RULE_EXACT_MATCH}, {@link #RULE_PREFIX_MATCH},
	 *            {@link #RULE_PATTERN_MATCH}, {@link #RULE_CASE_SENSITIVE},
	 *            {@link #RULE_CAMELCASE_MATCH} combined with one of following
	 *            values: {@link #RULE_EXACT_MATCH}, {@link #RULE_PREFIX_MATCH},
	 *            {@link #RULE_PATTERN_MATCH} or {@link #RULE_CAMELCASE_MATCH}.
	 *            e.g. {@link #RULE_EXACT_MATCH} | {@link #RULE_CASE_SENSITIVE}
	 *            if an exact and case sensitive match is requested,
	 *            {@link #RULE_PREFIX_MATCH} if a prefix non case sensitive
	 *            match is requested or {@link #RULE_EXACT_MATCH} if a non case
	 *            sensitive and erasure match is requested.<br>
	 *            Note also that default behavior for generic types/methods
	 *            search is to find exact matches.
