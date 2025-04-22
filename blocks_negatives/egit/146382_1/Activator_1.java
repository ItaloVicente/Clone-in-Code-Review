			if (!SshClientType.JSCH.name().equalsIgnoreCase(sshClient)) {
				logWarning(
						MessageFormat.format(
								CoreText.Activator_SshClientUnknown, sshClient),
						null);
			}
