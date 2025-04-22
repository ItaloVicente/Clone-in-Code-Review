		boolean value = internalTest(receiver, property, args, expectedValue);
		boolean trace = GitTraceLocation.PROPERTIESTESTER.isActive();
		if (trace)
			GitTraceLocation
					.getTrace()
					.trace(GitTraceLocation.PROPERTIESTESTER.getLocation(),
							"prop "	+ property + " of " + receiver + " = " + value + ", expected = " + expectedValue); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		return value;
	}

	private boolean internalTest(Object receiver, String property, Object[] args,
			Object expectedValue) {
