		String sshClient = Platform.getPreferencesService().getString(pluginId,
				GitCorePreferences.core_sshClient, "jsch", null); //$NON-NLS-1$
		SshSessionFactory previous = SshSessionFactory.getInstance();
		if (SshClientType.APACHE.name().equalsIgnoreCase(sshClient)) {
			if (previous instanceof EGitSshdSessionFactory) {
				return;
			}
			logInfo(CoreText.Activator_SshClientUsingApache);
			SshSessionFactory.setInstance(new EGitSshdSessionFactory());
		} else {
			if (previous instanceof EclipseSshSessionFactory) {
				return;
			}
			if (!SshClientType.JSCH.name().equalsIgnoreCase(sshClient)) {
				logWarning(
						MessageFormat.format(
								CoreText.Activator_SshClientUnknown, sshClient),
						null);
			}
			ServiceReference ssh = context
					.getServiceReference(IJSchService.class.getName());
			if (ssh != null) {
				SshSessionFactory.setInstance(new EclipseSshSessionFactory(
						(IJSchService) context.getService(ssh)));
			} else {
				logWarning(CoreText.Activator_SshClientNoJsch, null);
				if (previous instanceof EGitSshdSessionFactory) {
					return;
				}
				SshSessionFactory.setInstance(new EGitSshdSessionFactory());
			}
		}
		if (previous instanceof SshdSessionFactory) {
			((SshdSessionFactory) previous).close();
