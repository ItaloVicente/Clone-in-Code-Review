	 * explicit wild-cards '*' and '?' and is inherently case sensitive. Can
	 * match only subset of name/pattern, considering end positions as
	 * non-inclusive. The subpattern is defined by the patternStart and
	 * patternEnd positions. <br>
	 * CamelCase denotes the convention of writing compound names without
	 * spaces, and capitalizing every term. This function recognizes both upper
	 * and lower CamelCase, depending whether the leading character is
	 * capitalized or not. The leading part of an upper CamelCase pattern is
	 * assumed to contain a sequence of capitals which are appearing in the
	 * matching name; e.g. 'NPE' will match 'NullPointerException', but not
	 * 'NewPerfData'. A lower CamelCase pattern uses a lowercase first
	 * character. In Java, type names follow the upper CamelCase convention,
	 * whereas method or field names follow the lower CamelCase convention. <br>
	 * The pattern may contain lowercase characters, which will be match in a
	 * case sensitive way. These characters must appear in sequence in the name.
	 * For instance, 'NPExcep' will match 'NullPointerException', but not
	 * 'NullPointerExCEPTION' or 'NuPoEx' will match 'NullPointerException', but
	 * not 'NoPointerException'. <br>
