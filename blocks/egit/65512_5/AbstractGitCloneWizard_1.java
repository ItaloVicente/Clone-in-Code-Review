package org.eclipse.egit.ui.internal;

import java.util.LinkedHashMap;

import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jgit.annotations.NonNull;

public final class KnownHosts {

	private static final String KNOWN_HOSTS_KEY = "EGit.KnownHosts"; //$NON-NLS-1$

	private static final String[] DEFAULT_HOSTS = { "git.eclipse.org", //$NON-NLS-1$
			"github.com", "bitbucket.org" }; //$NON-NLS-1$ //$NON-NLS-2$

	private static HostStore knownHosts;

	private static boolean modified;

	private KnownHosts() {
	}

	public static boolean isKnownHost(String hostName) {
		return hostName != null && getKnownHosts().containsKey(hostName);
	}

	public static void addKnownHost(@NonNull String hostName) {
		getKnownHosts().put(hostName, null);
		modified = true; // At least the access order has changed
	}

	public static void store() {
		if (modified) {
			String[] values = new String[knownHosts.size()];
			Activator.getDefault().getDialogSettings().put(KNOWN_HOSTS_KEY,
					knownHosts.keySet().toArray(values));
			modified = false;
		}
	}

	private static HostStore getKnownHosts() {
		if (knownHosts == null) {
			IDialogSettings settings = Activator.getDefault()
					.getDialogSettings();
			String[] values = settings.getArray(KNOWN_HOSTS_KEY);
			if (values == null) {
				settings.put(KNOWN_HOSTS_KEY, DEFAULT_HOSTS);
				values = DEFAULT_HOSTS;
			}
			knownHosts = new HostStore(values.length);
			for (int i = values.length - 1; i >= 0; i--) {
				String host = values[i];
				if (host != null && !host.isEmpty()) {
					knownHosts.put(host, null);
				}
			}
		}
		return knownHosts;
	}

	@SuppressWarnings("serial")
	private static class HostStore extends LinkedHashMap<String, String> {

		private static final int MAXIMUM_SIZE = 200;

		public HostStore(int size) {
			super(size < 10 ? 10 : size, 0.75f, true);
		}

		@Override
		protected boolean removeEldestEntry(
				java.util.Map.Entry<String, String> eldest) {
			return size() > MAXIMUM_SIZE;
		}
	}

}
