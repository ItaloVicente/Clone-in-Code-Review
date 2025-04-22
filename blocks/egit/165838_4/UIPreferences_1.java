package org.eclipse.egit.core.settings;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.egit.core.RepositoryUtil;

public final class GitSettings {

	private GitSettings() {
	}

	public static int getRemoteConnectionTimeout() {
		return Platform.getPreferencesService().getInt(Activator.getPluginId(),
				GitCorePreferences.core_remoteConnectionTimeout, 60, null);
	}

	public static Path getDefaultRepositoryDir() {
		return Paths.get(RepositoryUtil.getDefaultRepositoryDir());
	}

	public static Collection<Path> getConfiguredRepositoryDirectories() {
		return Activator.getDefault().getRepositoryUtil().getRepositories()
				.stream().map(Paths::get).collect(Collectors.toSet());
	}

	public static void addConfiguredRepository(Path gitDir)
			throws IllegalArgumentException {
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				Objects.requireNonNull(gitDir).toFile());
	}
}
