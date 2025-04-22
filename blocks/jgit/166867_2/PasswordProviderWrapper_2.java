	private PerSessionState getState(SessionContext context) {
		PerSessionState state = context.getAttribute(STATE);
		if (state == null) {
			state = new PerSessionState();
			state.delegate = factory.get();
			Integer maxNumberOfAttempts = context
					.getInteger(ClientAuthenticationManager.PASSWORD_PROMPTS);
			if (maxNumberOfAttempts != null
					&& maxNumberOfAttempts.intValue() > 0) {
				state.delegate.setAttempts(maxNumberOfAttempts.intValue());
			} else {
				state.delegate.setAttempts(
						ClientAuthenticationManager.DEFAULT_PASSWORD_PROMPTS);
			}
			context.setAttribute(STATE
		}
		return state;
