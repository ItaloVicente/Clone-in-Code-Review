	boolean matchesFilter(StackTraceElement stackFrame) {
		String className = stackFrame.getClassName();
		String methodName = stackFrame.getMethodName();
		if (filterPatterns.length != 0) {
			compoundName.reset(className, methodName);
			for (Pattern pattern : filterPatterns) {
				if (pattern.matcher(compoundName).matches()) {
