package org.eclipse.urischeme.internal.registration;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;

public class RegistryFileWriter {

	private String key_scheme;
	private String key_shell;
	private String key_open;
	private String key_command;
	String launcherPath;
	private static final String ATTRIBUTE_DEFAULT = null;
	private static final String ATTRIBUTE_EXECUTABLE = "Executable"; //$NON-NLS-1$
	private static final String ATTRIBUTE_PROTOCOL_MARKER = "URL Protocol"; //$NON-NLS-1$
	public RegistryFileWriter() {
		this(System.getProperty("eclipse.launcher"), // NOPMD //$NON-NLS-1$
				System.getProperty("eclipse.home.location")); // NOPMD //$NON-NLS-1$
	}

	public RegistryFileWriter(String launcher, String homeLocation) {
		Assert.isNotNull(homeLocation, "home location must not be null"); //$NON-NLS-1$
		URI homeLocURI = filePathToURI(homeLocation);
		setLauncherPath(getLauncher(launcher, homeLocURI));
	}

	public void addScheme(String scheme, String schemeDescription) {
		try {
			changeKeys(scheme);
			getRegisteredHandler(); // TODO :Not capturing the return value.We are just calling it to avoid registry
			WinRegistry.setValueForKey(getKey_scheme(), ATTRIBUTE_PROTOCOL_MARKER, ""); //$NON-NLS-1$
			WinRegistry.setValueForKey(getKey_scheme(), ATTRIBUTE_DEFAULT, schemeDescription);
			WinRegistry.setValueForKey(getKey_command(), ATTRIBUTE_EXECUTABLE, getLauncherPath());
			String openCommand = quote(getLauncherPath()) + " " + quote("%1"); //$NON-NLS-1$ //$NON-NLS-2$
			WinRegistry.setValueForKey(getKey_command(), ATTRIBUTE_DEFAULT, openCommand);
		} catch (WinRegistryException ex) {
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}

	public void removeScheme(String scheme) {
		changeKeys(scheme);
		if (getRegisteredHandler() == null) {
			return;
		}
		try {
			WinRegistry.deleteKey(getKey_command());
			WinRegistry.deleteKey(getKey_open());
			WinRegistry.deleteKey(getKey_shell());
			WinRegistry.deleteKey(getKey_scheme());
		} catch (WinRegistryException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("javadoc") // TODO : check if suppress warnings is valid
	public String getRegisteredHandler() {
		try {
			String marker = WinRegistry.getValueForKey(getKey_scheme(), ATTRIBUTE_PROTOCOL_MARKER);
			if (marker == null) {
				return null;
			}
			String command = WinRegistry.getValueForKey(getKey_command(), ATTRIBUTE_DEFAULT);
			if (command == null) {
				return null;
			}
			String exec = WinRegistry.getValueForKey(getKey_command(), ATTRIBUTE_EXECUTABLE);
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
		setKey_scheme("Software\\Classes\\"); //$NON-NLS-1$
		setKey_scheme(getKey_scheme() + scheme);
		setKey_shell(getKey_scheme() + "\\shell"); //$NON-NLS-1$
		setKey_open(getKey_shell() + "\\open"); //$NON-NLS-1$
		setKey_command(getKey_open() + "\\command"); //$NON-NLS-1$
	}

	private static String quote(String s) {
		return String.format("\"%s\"", s); //$NON-NLS-1$
	}

	static URI filePathToURI(String path) {
		try {
			return new URI(path);
		} catch (URISyntaxException e) { // NOPMD
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

	private static String getLauncherFromHomeLocation(URI homeLocation) {
		if (homeLocation != null) {
			File homeLoc = new File(homeLocation);
			if (homeLoc.exists() && homeLoc.isDirectory()) {
				File[] children = homeLoc.listFiles();
				for (File child : children) {
					String name = child.getName();
					if (name.endsWith(".exe")) { //$NON-NLS-1$
						return child.getAbsolutePath();
					}
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
	public String getKey_scheme() {
		return key_scheme;
	}

	@SuppressWarnings("javadoc")
	public void setKey_scheme(String key_scheme) {
		this.key_scheme = key_scheme;
	}

	@SuppressWarnings("javadoc")
	public String getKey_shell() {
		return key_shell;
	}

	@SuppressWarnings("javadoc")
	public void setKey_shell(String key_shell) {
		this.key_shell = key_shell;
	}

	@SuppressWarnings("javadoc")
	public String getKey_open() {
		return key_open;
	}

	@SuppressWarnings("javadoc")
	public void setKey_open(String key_open) {
		this.key_open = key_open;
	}

	@SuppressWarnings("javadoc")
	public String getKey_command() {
		return key_command;
	}

	@SuppressWarnings("javadoc")
	public void setKey_command(String key_command) {
		this.key_command = key_command;
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
