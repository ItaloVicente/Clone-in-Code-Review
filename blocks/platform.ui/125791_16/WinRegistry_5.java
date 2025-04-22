package org.eclipse.urischeme.internal.registration;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Path;

public class RegistryWriter implements IRegistryWriter {

	private String key_software_classes;
	private String key_shell;
	private String key_open;
	private String key_command;
	String launcherPath;
	private static final String ATTRIBUTE_DEFAULT = null;
	private static final String ATTRIBUTE_EXECUTABLE = "Executable"; //$NON-NLS-1$
	private static final String ATTRIBUTE_PROTOCOL_MARKER = "URL Protocol"; //$NON-NLS-1$

	private IWinRegistry winRegistry = null;
	private IFileProvider fileProvider = null;

	public RegistryWriter() {
		this(new WinRegistry(), new FileProvider());
	}

	public RegistryWriter(IWinRegistry winRegistry, IFileProvider fileProvider) {
		String launcher = System.getProperty("eclipse.launcher"); //$NON-NLS-1$
		String homeLocation = System.getProperty("eclipse.home.location"); //$NON-NLS-1$

		Assert.isNotNull(launcher, "launcher must not be null"); //$NON-NLS-1$
		Assert.isNotNull(homeLocation, "home location must not be null"); //$NON-NLS-1$

		this.winRegistry = winRegistry;
		this.fileProvider = fileProvider;
		URI homeLocURI = filePathToURI(homeLocation);
		setLauncherPath(getLauncher(launcher, homeLocURI));
	}

	@Override
	public void addScheme(String scheme) throws WinRegistryException {
		Util.assertUriSchemeIsLegal(scheme);
		getRegisteredHandlerPath(scheme);

		winRegistry.setValueForKey(key_software_classes, ATTRIBUTE_PROTOCOL_MARKER, ""); //$NON-NLS-1$
		winRegistry.setValueForKey(key_software_classes, ATTRIBUTE_DEFAULT, "URL:" + scheme); //$NON-NLS-1$
		winRegistry.setValueForKey(key_command, ATTRIBUTE_EXECUTABLE, getLauncherPath());
		String openCommand = IRegistryWriter.quote(getLauncherPath()) + " " + IRegistryWriter.quote("%1"); //$NON-NLS-1$ //$NON-NLS-2$
		winRegistry.setValueForKey(key_command, ATTRIBUTE_DEFAULT, openCommand);
	}

	@Override
	public void removeScheme(String scheme) throws WinRegistryException {
		Util.assertUriSchemeIsLegal(scheme);
		if (getRegisteredHandlerPath(scheme) == null) {
			return;
		}
		winRegistry.deleteKey(key_command);
		winRegistry.deleteKey(key_open);
		winRegistry.deleteKey(key_shell);
		winRegistry.deleteKey(key_software_classes);
	}

	@Override
	public String getRegisteredHandlerPath(String scheme) throws WinRegistryException {
		changeKeys(scheme);
		String marker = winRegistry.getValueForKey(key_software_classes, ATTRIBUTE_PROTOCOL_MARKER);
		if (marker == null) {
			return null;
		}
		String command = winRegistry.getValueForKey(key_command, ATTRIBUTE_DEFAULT);
		if (command == null) {
			return null;
		}
		String exec = winRegistry.getValueForKey(key_command, ATTRIBUTE_EXECUTABLE);
		if (exec == null) {
			return null;
		}
		if (!fileProvider.fileExists(exec)) {
			return null;
		}
		return exec;
	}

	void changeKeys(String scheme) {
		key_software_classes = "Software\\Classes\\"; //$NON-NLS-1$
		key_software_classes += scheme;
		key_shell = key_software_classes + "\\shell"; //$NON-NLS-1$
		key_open = key_shell + "\\open"; //$NON-NLS-1$
		key_command = key_open + "\\command"; //$NON-NLS-1$
	}

	String getLauncherPath() {
		return launcherPath;
	}

	void setLauncherPath(String launcherPath) {
		this.launcherPath = launcherPath;
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

	private String getLauncher(final String launcher, final URI homeLocation) {
		if (launcher != null) {
			if (this.fileProvider.fileExists(launcher) && !fileProvider.isDirectory(launcher)) {
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
}
