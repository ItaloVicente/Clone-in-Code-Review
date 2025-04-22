
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.eclipse.jgit.pgm.internal.CLIText;

@Command(common = true
class Version extends TextBuiltin {
	@Override
	protected void run() {
		String version = getImplementationVersion();

		if (version == null) {
			version = getBundleVersion();
		}

		if (version == null) {
			throw die(CLIText.get().cannotReadPackageInformation);
		}

		try {
			outw.println(
					MessageFormat.format(CLIText.get().jgitVersion
		} catch (IOException e) {
			throw die(e.getMessage()
		}
	}

	@Override
	protected final boolean requiresRepository() {
		return false;
	}

	private String getImplementationVersion() {
		Package pkg = getClass().getPackage();
		return (pkg == null) ? null : pkg.getImplementationVersion();
	}

	private String getBundleVersion() {
		ClassLoader cl = getClass().getClassLoader();
		if (cl instanceof URLClassLoader) {
			URL url = ((URLClassLoader) cl).findResource(JarFile.MANIFEST_NAME);
			if (url != null)
				return getBundleVersion(url);
		}
		return null;
	}

	private static String getBundleVersion(URL url) {
		try (InputStream is = url.openStream()) {
			Manifest manifest = new Manifest(is);
		} catch (IOException e) {
		}
		return null;
	}
}
