		if (changeablePath) {
			try {
				IEclipsePreferences node = new InstanceScope()
						.getNode(org.eclipse.egit.core.Activator
								.getPluginId());
				node.remove(GitCorePreferences.core_gitPrefix);
				node.flush();
				File gitPrefix = FS.detect().gitPrefix();
				FS.DETECTED.setGitPrefix(gitPrefix);
			} catch (Exception e1) {
				Activator
						.logError(
								UIText.ConfigurationEditorComponent_CannotChangeGitPrefixError,
								e1);
			}
		}
