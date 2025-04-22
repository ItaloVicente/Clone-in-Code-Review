package org.eclipse.urischeme.internal.registration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;

public class RegistryWriter {

	private String KEY_ADT;
	private String KEY_SHELL;
	private String KEY_OPEN;
	private String KEY_COMMAND;
	String launcherPath;
	private static final String ATTRIBUTE_DEFAULT = null;
	private static final String ATTRIBUTE_EXECUTABLE = "Executable"; //$NON-NLS-1$
	private static final String ATTRIBUTE_PROTOCOL_MARKER = "URL Protocol"; //$NON-NLS-1$

	public RegistryWriter() {
		this(System.getProperty("eclipse.launcher"), //$NON-NLS-1$
				System.getProperty("eclipse.home.location")); //$NON-NLS-1$
	}

	@SuppressWarnings("javadoc") // TODO :Re look if JAVADOC needs to be suppressed
	public RegistryWriter(String launcher, String homeLocation) {
		Assert.isNotNull(homeLocation, "home location must not be null"); //$NON-NLS-1$
		URI homeLocURI = filePathToURI(homeLocation);
		setLauncherPath(getLauncher(launcher, homeLocURI));
	}

	public void addScheme(String scheme, String schemeDescription) {
		try {
			Util.assertUriSchemeIsLegal(scheme); // TODO: Is this check needed(Does Windows support only letters as
			getRegisteredHandlerPath(scheme);
			WinRegistry.setValueForKey(KEY_ADT, ATTRIBUTE_PROTOCOL_MARKER, ""); //$NON-NLS-1$
			WinRegistry.setValueForKey(KEY_ADT, ATTRIBUTE_DEFAULT, "URL:" + schemeDescription); //$NON-NLS-1$
			WinRegistry.setValueForKey(KEY_COMMAND, ATTRIBUTE_EXECUTABLE, getLauncherPath());
			String openCommand = quote(getLauncherPath()) + " " + quote("%1"); //$NON-NLS-1$ //$NON-NLS-2$
			WinRegistry.setValueForKey(KEY_COMMAND, ATTRIBUTE_DEFAULT, openCommand);
		} catch (WinRegistryException ex) {
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}

	public void removeScheme(String scheme) {
		if (getRegisteredHandlerPath(scheme) == null) {
			return;
		}
		Util.assertUriSchemeIsLegal(scheme); // TODO:Same like addScheme
		try {
			WinRegistry.deleteKey(KEY_COMMAND);
			WinRegistry.deleteKey(KEY_OPEN);
			WinRegistry.deleteKey(KEY_SHELL);
			WinRegistry.deleteKey(KEY_ADT);
		} catch (WinRegistryException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public String getRegisteredHandlerPath(String scheme) {
		changeKeys(scheme);
		try {
			String marker = WinRegistry.getValueForKey(KEY_ADT, ATTRIBUTE_PROTOCOL_MARKER);
			if (marker == null) {
				return null;
			}
			String command = WinRegistry.getValueForKey(KEY_COMMAND, ATTRIBUTE_DEFAULT);
			if (command == null) {
				return null;
			}
			String exec = WinRegistry.getValueForKey(KEY_COMMAND, ATTRIBUTE_EXECUTABLE);
			if (exec == null) {
				return null;
			}
			File execFile = new File(exec);
			if (!execFile.exists()) {
				return null;
			}
			return execFile.getAbsolutePath();
		} catch (WinRegistryException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("javadoc")
	public void changeKeys(String scheme) {
		KEY_ADT = "Software\\Classes\\"; //$NON-NLS-1$
		KEY_ADT += scheme;
		KEY_SHELL = KEY_ADT + "\\shell"; //$NON-NLS-1$
		KEY_OPEN = KEY_SHELL + "\\open"; //$NON-NLS-1$
		KEY_COMMAND = KEY_OPEN + "\\command"; //$NON-NLS-1$
	}

	private static String quote(String s) {
		return String.format("\"%s\"", s); //$NON-NLS-1$
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

	private static String getLauncher(String launcher, URI homeLocation) {
		if (launcher != null) {
			File launcherFile = new File(launcher);
			if (launcherFile.exists() && !launcherFile.isDirectory()) {
				return launcher;
			}
		}
		return getLauncherFromHomeLocation(homeLocation);
	}

	@SuppressWarnings("resource")
	private static String getLauncherFromHomeLocation(URI homeLocation) {
		if (homeLocation != null) {
			File homeLoc = new File(homeLocation);
			if (homeLoc.exists() && homeLoc.isDirectory()) {
				try {
					DirectoryStream<java.nio.file.Path> stream = Files.newDirectoryStream(homeLoc.toPath(), "*.exe"); //$NON-NLS-1$
					for (java.nio.file.Path path : stream) {
						return path.toString(); // TODO : is Suppress warnings fine?
					}
				} catch (IOException e) {
					throw new IllegalStateException(e.getMessage(), e); // TODO : is this exception fine?
				}
				File parentFile = homeLoc.getParentFile();
				if (parentFile != null) {
					return getLauncherFromHomeLocation(parentFile.toURI());
				}
			}
		}
		return null;
	}

	@SuppressWarnings("javadoc")
	public String getLauncherPath() {
		return launcherPath;
	}

	@SuppressWarnings("javadoc")
	public void setLauncherPath(String launcherPath) {
		this.launcherPath = launcherPath;
	}

	private static String stripProtocol(String path) {
		return new Path(path).setDevice(null).makeRelative().toOSString();
	}
}
