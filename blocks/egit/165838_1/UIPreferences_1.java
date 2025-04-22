package org.eclipse.egit.core.settings;

import java.io.File;
import java.util.Collection;
import java.util.Objects;

import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;

public final class GitSettings {

	private GitSettings() {
	}

	public static int getRemoteConnectionTimeout() {
		return Platform.getPreferencesService().getInt(Activator.getPluginId(),
				GitCorePreferences.core_remoteConnectionTimeout, 60, null);
	}

	public static Collection<String> getConfiguredRepositoryDirectories() {
		return Activator.getDefault().getRepositoryUtil().getRepositories();
	}

	public static void addConfiguredRepository(File gitDir)
			throws IllegalArgumentException {
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(Objects.requireNonNull(gitDir));
	}
}
