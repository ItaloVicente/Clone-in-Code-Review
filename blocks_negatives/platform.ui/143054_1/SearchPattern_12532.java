	 * Can be combined to {@link #RULE_PREFIX_MATCH} match rule. For example,
	 * when prefix match rule is combined with Camel Case match rule,
	 * <code>"nPE"</code> pattern will match <code>nPException</code>. <br>
	 * Match rule {@link #RULE_PATTERN_MATCH} may also be combined but both
	 * rules will not be used simultaneously as they are mutually exclusive.
	 * Used match rule depends on whether string pattern contains specific
	 * pattern characters (e.g. '*' or '?') or not. If it does, then only
	 * Pattern match rule will be used, otherwise only Camel Case match will be
	 * used. For example, with <code>"NPE"</code> string pattern, search will
	 * only use Camel Case match rule, but with <code>N*P*E*</code> string
	 * pattern, it will use only Pattern match rule.
