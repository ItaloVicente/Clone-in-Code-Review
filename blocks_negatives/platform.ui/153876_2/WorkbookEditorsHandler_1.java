			SearchPattern patternMatcher = new SearchPattern();
			if (pattern.indexOf('*') != 0 && pattern.indexOf('?') != 0) {
				pattern = '*' + pattern;
			}
			patternMatcher.setPattern(pattern);
			searchPattern = patternMatcher;
