	protected abstract class WildcardItemsFilter extends ItemsFilter {

		@Override
		protected boolean matches(String text) {
			String pattern = patternMatcher.getPattern();
			if (pattern.indexOf("*") != 0 & pattern.indexOf("?") != 0 & pattern.indexOf(".") != 0) {//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				pattern = "*" + pattern; //$NON-NLS-1$
				patternMatcher.setPattern(pattern);
			}
			return patternMatcher.matches(text);
		}
	}

