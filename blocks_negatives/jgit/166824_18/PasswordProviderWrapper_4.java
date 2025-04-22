			Integer maxNumberOfAttempts = context
					.getInteger(ClientAuthenticationManager.PASSWORD_PROMPTS);
			if (maxNumberOfAttempts != null
					&& maxNumberOfAttempts.intValue() > 0) {
				state.delegate.setAttempts(maxNumberOfAttempts.intValue());
			} else {
				state.delegate.setAttempts(
						ClientAuthenticationManager.DEFAULT_PASSWORD_PROMPTS);
			}
