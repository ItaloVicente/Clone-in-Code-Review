		List<StackFrame> stackFrames = new ArrayList<StackFrame>(filters.length);
		List<Pattern> stackPatterns = new ArrayList<Pattern>(filters.length);
		for (String filter : filters) {
			if (containsWildcards(filter)) {
				Pattern pattern = createPattern(filter);
				stackPatterns.add(pattern);
			} else {
				int lastDot = filter.lastIndexOf('.');
				stackFrames.add(lastDot >= 0 ?
						new StackFrame(filter.substring(0, lastDot), filter.substring(lastDot + 1)) :
						new StackFrame("", filter)); //$NON-NLS-1$
			}
