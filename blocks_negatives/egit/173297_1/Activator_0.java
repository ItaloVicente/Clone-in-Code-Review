	@SuppressWarnings("unchecked")
	private void setupSSH(final BundleContext context) {
		String sshClient = Platform.getPreferencesService().getString(pluginId,
				GitCorePreferences.core_sshClient, "apache", null); //$NON-NLS-1$
		SshSessionFactory previous = SshSessionFactory.getInstance();
		if (SshClientType.JSCH.name().equalsIgnoreCase(sshClient)) {
			if (previous instanceof EclipseSshSessionFactory) {
				return;
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
		}
		if (previous instanceof SshdSessionFactory) {
			((SshdSessionFactory) previous).close();
		}
	}

