	@Override
	protected boolean isFiltered() {
		return true;
	}

	SearchPattern getMatcher() {
		return searchPattern;
	}

	@Override
	protected void setMatcherString(String pattern) {
		if (pattern.length() == 0) {
			searchPattern = null;
		} else {
			SearchPattern patternMatcher = new SearchPattern();
			searchPattern = patternMatcher;
		}
	}

	@Override
	protected ViewerFilter getFilter() {
		return new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				SearchPattern matcher = getMatcher();
				if (matcher == null || !(viewer instanceof TableViewer)) {
					return true;
				}
				String matchName = null;
				if (element instanceof ViewReference) {
					matchName = ((ViewReference) element).getPartName();
				}
				if (matchName == null) {
					return false;
				}
				return matcher.matches(matchName);
			}
		};
	}

