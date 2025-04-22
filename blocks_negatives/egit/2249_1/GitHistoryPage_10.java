			paths = new ArrayList<String>(0);
		}
		return paths;
	}

	/**
	 * @param message
	 *            the message to display instead of the control
	 */
	public void setErrorMessage(final String message) {
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation(), message);
		getHistoryPageSite().getShell().getDisplay().asyncExec(new Runnable() {
