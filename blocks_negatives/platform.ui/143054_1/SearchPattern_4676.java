	 * <li>{@link #RULE_PATTERN_MATCH} without any '*' or '?' in string pattern: pattern match bit
	 * is unset,</li>
	 * <li>{@link #RULE_PATTERN_MATCH} and {@link #RULE_PREFIX_MATCH} bits simultaneously set:
	 * prefix match bit is unset,</li>
	 * <li>{@link #RULE_PATTERN_MATCH} and {@link #RULE_CAMELCASE_MATCH} bits simultaneously set:
	 * camel case match bit is unset,</li>
	 * <li>{@link #RULE_CAMELCASE_MATCH} with invalid combination of uppercase and lowercase
	 * characters: camel case match bit is unset and replaced with prefix match pattern,</li>
	 * <li>{@link #RULE_CAMELCASE_MATCH} combined with {@link #RULE_PREFIX_MATCH} and
	 * {@link #RULE_CASE_SENSITIVE} bits is reduced to only {@link #RULE_CAMELCASE_MATCH} as Camel
	 * Case search is already prefix and case sensitive,</li>
