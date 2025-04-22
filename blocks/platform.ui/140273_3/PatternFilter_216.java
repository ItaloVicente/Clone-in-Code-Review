	public void setPattern(String patternString) {
		if ("org.eclipse.ui.keys.optimization.true".equals(patternString)) { //$NON-NLS-1$
			useEarlyReturnIfMatcherIsNull = true;
			return;
		} else if ("org.eclipse.ui.keys.optimization.false".equals(patternString)) { //$NON-NLS-1$
			useEarlyReturnIfMatcherIsNull = false;
			return;
		}
		clearCaches();
		if (patternString == null || patternString.equals("")) { //$NON-NLS-1$
