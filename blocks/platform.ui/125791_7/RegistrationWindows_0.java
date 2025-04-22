package org.eclipse.urischeme;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;

import org.eclipse.core.runtime.Path;

public interface IRegistryWriter {

	static String quote(String string) {
		return String.format("\"%s\"", string); //$NON-NLS-1$
	}

	static URI filePathToURI(String path) {
		try {
			return new URI(path);
		} catch (URISyntaxException e) {
			if (path.startsWith("file:")) { //$NON-NLS-1$
				String strippedPath = stripProtocol(path);
				return new File(strippedPath).toURI();
			}
			return new File(path).toURI();
		}
	}

	static String stripProtocol(String path) {
		return new Path(path).setDevice(null).makeRelative().toOSString();
	}

	static String getLauncher(final String launcher, final URI homeLocation) {
		if (launcher != null) {
			File launcherFile = new File(launcher);
			if (launcherFile.exists() && !launcherFile.isDirectory()) {
				return launcher;
			}
		}
		return getLauncherFromHomeLocation(homeLocation);
	}

	static String getLauncherFromHomeLocation(URI homeLocation) {
		String fetchedPath;
		if (homeLocation != null) {
			File homeLoc = new File(homeLocation);
			if (homeLoc.exists() && homeLoc.isDirectory()) {
				try {
					DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(homeLoc.toPath(), "*.exe"); //$NON-NLS-1$
					for (java.nio.file.Path path : stream) {
						fetchedPath = path.toString();
						stream.close();
						return fetchedPath;
					}
					stream.close();
				} catch (IOException e) {
					throw new IllegalStateException(e.getMessage(), e);
				}
				File parentFile = homeLoc.getParentFile();
				if (parentFile != null) {
					return getLauncherFromHomeLocation(parentFile.toURI());
				}
			}
		}
		return null;
	}

	void addScheme(String scheme) throws IllegalArgumentException;


	void removeScheme(String scheme) throws IllegalArgumentException;

	String getRegisteredHandlerPath(String scheme);

}
