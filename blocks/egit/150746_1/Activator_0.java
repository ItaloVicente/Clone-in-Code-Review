	private void setupHttp() {
		String sshClient = Platform.getPreferencesService().getString(pluginId,
				GitCorePreferences.core_httpClient, "jdk", null); //$NON-NLS-1$
		if (HttpClientType.APACHE.name().equalsIgnoreCase(sshClient)) {
			HttpTransport.setConnectionFactory(new HttpClientConnectionFactory());
		} else {
			if (!HttpClientType.JDK.name().equalsIgnoreCase(sshClient)) {
				logWarning(
						MessageFormat.format(
								CoreText.Activator_HttpClientUnknown, sshClient),
						null);
			}
			HttpTransport.setConnectionFactory(new JDKHttpConnectionFactory());
		}
	}

