	private void setUserName(Session session
		if (userName == null || userName.isEmpty()
				|| userName.equals(session.getUserName())) {
			return;
		}
		try {
			Class<?>[] parameterTypes = { String.class };
			Method method = Session.class.getDeclaredMethod("setUserName"
					parameterTypes);
			method.setAccessible(true);
			method.invoke(session
		} catch (NullPointerException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.error(MessageFormat.format(JGitText.get().sshUserNameError
					userName
		}
	}

