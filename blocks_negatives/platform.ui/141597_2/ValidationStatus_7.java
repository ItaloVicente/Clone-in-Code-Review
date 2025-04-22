		final int prime = 31;
		int result = 1;

		String message = getMessage();
		int severity = getSeverity();
		Throwable throwable = getException();

		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + severity;
		result = prime * result
				+ ((throwable == null) ? 0 : throwable.hashCode());
		return result;
