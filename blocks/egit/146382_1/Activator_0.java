		} else {
			if (!SshClientType.APACHE.name().equalsIgnoreCase(sshClient)) {
				logWarning(
						MessageFormat.format(
								CoreText.Activator_SshClientUnknown, sshClient),
						null);
			}
			if (previous instanceof EGitSshdSessionFactory) {
				return;
			}
			SshSessionFactory.setInstance(new EGitSshdSessionFactory());
