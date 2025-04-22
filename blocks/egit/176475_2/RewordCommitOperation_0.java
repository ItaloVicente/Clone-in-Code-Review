package org.eclipse.egit.core.internal.signing;

import org.eclipse.core.runtime.Platform;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.jgit.gpg.bc.BouncyCastleGpgSignerFactory;
import org.eclipse.jgit.lib.GpgSigner;

public class GpgSetup {

	private GpgSetup() {
	}

	private enum Signer {
		BC, GPG
	}

	private static final Object LOCK = new Object();

	private static Signer current;

	public static GpgSigner getDefault() {
		Signer signer = getSigner();
		synchronized (LOCK) {
			if (signer != current) {
				current = signer;
				switch (signer) {
				case BC:
					GpgSigner.setDefault(BouncyCastleGpgSignerFactory.create());
					break;
				case GPG:
					GpgSigner.setDefault(new ExternalGpgSigner());
					break;
				default:
					throw new IllegalStateException("Unknown signer " + signer); //$NON-NLS-1$
				}
			}
			return GpgSigner.getDefault();
		}
	}

	private static Signer getSigner() {
		String pref = Platform.getPreferencesService().getString(
				Activator.PLUGIN_ID, GitCorePreferences.core_gpgSigner, null,
				null);
		for (Signer s : Signer.values()) {
			if (pref.equalsIgnoreCase(s.name())) {
				return s;
			}
		}
		Activator.logWarning("Invalid EGit core preference " //$NON-NLS-1$
				+ GitCorePreferences.core_gpgSigner, null);
		return Signer.BC;
	}
}
