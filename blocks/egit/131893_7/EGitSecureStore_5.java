package org.eclipse.egit.core.internal;

import static java.text.MessageFormat.format;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.jgit.annotations.NonNull;

public class SshPreferencesMirror {


	public static final SshPreferencesMirror INSTANCE = new SshPreferencesMirror();

	private IEclipsePreferences preferences;

	private IPreferenceChangeListener listener = event -> reloadPreferences();

	private File sshDirectory;

	private List<String> defaultIdentities;

	private String defaultMechanisms;

	private SshPreferencesMirror() {
	}

	public void start() {
		preferences = InstanceScope.INSTANCE.getNode("org.eclipse.jsch.core"); //$NON-NLS-1$
		if (preferences == null) {
			return;
		}
		preferences.addPreferenceChangeListener(listener);
		reloadPreferences();
	}

	public void stop() {
		if (preferences != null) {
			preferences.removePreferenceChangeListener(listener);
		}
	}

	private void reloadPreferences() {
		synchronized (this) {
			setSshDirectory();
			setDefaultIdentities();
			setPreferredAuthentications();
		}
	}

	private String get(@NonNull String key) {
		IEclipsePreferences pref = preferences;
		return pref == null ? null : pref.get(key, null);
	}

	private void setSshDirectory() {
		String sshDir = get("SSH2HOME"); //$NON-NLS-1$
		if (sshDir != null) {
			try {
				sshDirectory = Paths.get(sshDir).toFile();
			} catch (InvalidPathException e) {
				Activator.logWarning(
						format(CoreText.SshPreferencesMirror_invalidDirectory,
								sshDir),
						null);
			}
		}
		sshDirectory = null;
	}

	private void setDefaultIdentities() {
		String defaultKeys = get("PRIVATEKEY"); //$NON-NLS-1$
		if (defaultKeys == null || defaultKeys.isEmpty()) {
			defaultIdentities = null;
			return;
		}
		defaultIdentities = Arrays.asList(defaultKeys.trim().split("\\s*,\\s*")) //$NON-NLS-1$
				.stream().map(s -> {
					if (s.isEmpty()) {
						return null;
					}
					try {
						Paths.get(s);
						return s;
					} catch (InvalidPathException e) {
						Activator.logWarning(
								format(CoreText.SshPreferencesMirror_invalidKeyFile,
										s),
								null);
						return null;
					}
				}).filter(Objects::nonNull).collect(Collectors.toList());
	}

	private void setPreferredAuthentications() {
		String mechanisms = get("CVSSSH2PreferencePage.PREF_AUTH_METHODS"); //$NON-NLS-1$
		if (mechanisms == null || mechanisms.isEmpty()) {
			defaultMechanisms = null;
		}
		defaultMechanisms = mechanisms;
	}

	public File getSshDirectory() {
		synchronized (this) {
			return sshDirectory;
		}
	}

	public List<Path> getDefaultIdentities(@NonNull File sshDir) {
		synchronized (this) {
			if (defaultIdentities == null) {
				return null;
			}
			return defaultIdentities.stream()
					.map(s -> new File(sshDir, s).toPath())
					.filter(Files::exists).collect(Collectors.toList());
		}
	}

	public String getPreferredAuthentications() {
		synchronized (this) {
			return defaultMechanisms;
		}
	}
}
