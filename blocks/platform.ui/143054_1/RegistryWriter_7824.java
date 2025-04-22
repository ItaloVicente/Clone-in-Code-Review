package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.IScheme;
import org.eclipse.urischeme.ISchemeInformation;
public class RegistrationWindows implements IOperatingSystemRegistration {
	IRegistryWriter registryWriter;
	IFileProvider fileProvider;

	public RegistrationWindows() {
		this(new RegistryWriter(), new FileProvider());
	}

	public RegistrationWindows(IRegistryWriter registryWriter, IFileProvider fileProvider) {
		this.registryWriter = registryWriter;
		this.fileProvider = fileProvider;
	}

	@Override
	public void handleSchemes(Collection<IScheme> toAdd, Collection<IScheme> toRemove)
			throws Exception {
		for (IScheme scheme : toAdd) {
			registryWriter.addScheme(scheme.getName(), getEclipseLauncher());
		}
		for (IScheme scheme : toRemove) {
			registryWriter.removeScheme(scheme.getName());
		}
	}

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<IScheme> schemes) throws Exception {
		String launcher = getEclipseLauncher();

		List<ISchemeInformation> schemeInformations = new ArrayList<>();

		for (IScheme scheme : schemes) {
			SchemeInformation schemeInfo = new SchemeInformation(scheme.getName(),
					scheme.getDescription());
			String path = registryWriter.getRegisteredHandlerPath(schemeInfo.getName());
			if (path == null) {
				path = ""; //$NON-NLS-1$
			}
			schemeInfo.setHandled(path.equals(launcher));
			schemeInfo.setHandlerLocation(path);
			schemeInformations.add(schemeInfo);
		}
		return schemeInformations;
	}

	@Override
	public String getEclipseLauncher() {
		String launcher = getLauncherFromLauncherProperty();
		if (launcher != null) {
			return launcher;
		}
		return getLauncherFromHomeLocation();
	}

	@Override
	public boolean canOverwriteOtherApplicationsRegistration() {
		return true;
	}

	private String getLauncherFromLauncherProperty() {
		String launcher = System.getProperty("eclipse.launcher"); //$NON-NLS-1$
		if (launcher != null && this.fileProvider.fileExists(launcher) && !fileProvider.isDirectory(launcher)) {
			return launcher;
		}
		return null;
	}

	private String getLauncherFromHomeLocation() {
		String homeLocation = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		Assert.isNotNull(homeLocation, "home location must not be null"); //$NON-NLS-1$

		URL homeLocationUrl;
		try {
			homeLocationUrl = new URL(homeLocation);
		} catch (MalformedURLException e) {
			return null;
		}
		if (!"file".equals(homeLocationUrl.getProtocol())) { //$NON-NLS-1$
			return null;
		}

		String directory = fileProvider.getFilePath(homeLocationUrl);
		if (!fileProvider.fileExists(directory) || !fileProvider.isDirectory(directory)) {
			return null;
		}

		try (DirectoryStream<Path> stream = fileProvider.newDirectoryStream(directory, "*.exe")) { //$NON-NLS-1$
			for (Path path : stream) {
				return path.toString();
			}
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
		return null;
	}
}
