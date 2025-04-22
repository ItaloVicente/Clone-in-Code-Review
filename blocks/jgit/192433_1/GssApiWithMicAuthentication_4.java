	@Override
	public void signalAuthMethodSuccess(ClientSession session
			Buffer buffer) throws Exception {
		GssApiWithMicAuthenticationReporter reporter = session.getAttribute(
				GssApiWithMicAuthenticationReporter.GSS_AUTHENTICATION_REPORTER);
		if (reporter != null) {
			reporter.signalAuthenticationSuccess(session
					currentMechanism.toString());
		}
	}

	@Override
	public void signalAuthMethodFailure(ClientSession session
			boolean partial
			throws Exception {
		GssApiWithMicAuthenticationReporter reporter = session.getAttribute(
				GssApiWithMicAuthenticationReporter.GSS_AUTHENTICATION_REPORTER);
		if (reporter != null) {
			reporter.signalAuthenticationFailure(session
					currentMechanism.toString()
		}
	}
