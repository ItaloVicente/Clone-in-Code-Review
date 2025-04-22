			final Session session = createSession(hc, user, host, port, fs);
			if (pass != null)
				session.setPassword(pass);
			final String strictHostKeyCheckingPolicy = hc
					.getStrictHostKeyChecking();
			if (strictHostKeyCheckingPolicy != null)
				session.setConfig("StrictHostKeyChecking",
						strictHostKeyCheckingPolicy);
			final String pauth = hc.getPreferredAuthentications();
			if (pauth != null)
				session.setConfig("PreferredAuthentications", pauth);
			if (credentialsProvider != null
				&& (!hc.isBatchMode() || !credentialsProvider.isInteractive())) {
				session.setUserInfo(new CredentialsProviderUserInfo(session,
						credentialsProvider));
			}
			configure(hc, session);
