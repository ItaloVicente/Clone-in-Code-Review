		SshPreferencesMirror.INSTANCE.stop();
		if (sshClientChangeListener != null) {
			InstanceScope.INSTANCE.getNode(pluginId)
					.removePreferenceChangeListener(sshClientChangeListener);
			sshClientChangeListener = null;
		}
		SshSessionFactory current = SshSessionFactory.getInstance();
		if (current instanceof SshdSessionFactory) {
			((SshdSessionFactory) current).close();
		}
		if (proxyServiceTracker != null) {
			proxyServiceTracker.close();
			proxyServiceTracker = null;
		}
