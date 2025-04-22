		boolean value = internalTest(node, repository, property);
		boolean trace = GitTraceLocation.PROPERTIESTESTER.isActive();
		if (trace)
			GitTraceLocation
					.getTrace()
					.trace(GitTraceLocation.PROPERTIESTESTER.getLocation(),
							"prop "	+ property + " of " + receiver + " = " + value + ", expected = " + expectedValue); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		return computeResult(expectedValue, value);
	}
