package org.eclipse.urischeme.internal.registration;

import java.io.IOException;

import org.eclipse.urischeme.IOperatingSystemRegistration;

@SuppressWarnings("javadoc")
public class RegistrationMacOsX implements IOperatingSystemRegistration {

	private static final String PLIST_PATH_SUFFIX = "/Contents/Info.plist"; //$NON-NLS-1$
	private static final String LSREGISTER = "/System/Library/Frameworks/CoreServices.framework/Frameworks/LaunchServices.framework/Support/lsregister"; //$NON-NLS-1$
	private static final String UNREGISTER = "-u"; //$NON-NLS-1$
	private static final String RECURSIVE = "-r"; //$NON-NLS-1$

	private IFileProvider fileProvider;
	private IProcessExecutor processExecutor;

	public RegistrationMacOsX() {
		this(new FileProvider(), new ProcessExecutor());
	}

	public RegistrationMacOsX(IFileProvider fileProvider, IProcessExecutor processExecutor) {
		this.fileProvider = fileProvider;
		this.processExecutor = processExecutor;
	}

	@Override
	public void handleSchemes(Iterable<Scheme> toAdd, Iterable<Scheme> toRemove) throws IOException {
		String pathToEclipseApp = getPathToEclipseApp();

		changePlistFile(toAdd, toRemove, pathToEclipseApp);

		registerAppWithLsregister(pathToEclipseApp);
	}

	private void registerAppWithLsregister(String pathToEclipseApp) throws IOException {
		processExecutor.execute(LSREGISTER, UNREGISTER, pathToEclipseApp);
		processExecutor.execute(LSREGISTER, RECURSIVE, pathToEclipseApp);
	}

	private void changePlistFile(Iterable<Scheme> toAdd, Iterable<Scheme> toRemove, String pathToEclipseApp)
			throws IOException {
		String plistPath = pathToEclipseApp + PLIST_PATH_SUFFIX;

		PlistFileWriter writer = new PlistFileWriter(fileProvider.newReader(plistPath));

		for (Scheme scheme : toAdd) {
			writer.addScheme(scheme.getScheme(), scheme.getDescription());
		}

		for (Scheme scheme : toRemove) {
			writer.removeScheme(scheme.getScheme());
		}

		writer.writeTo(fileProvider.newWriter(plistPath));
	}

	private String getPathToEclipseApp() {
		String homeLocationProperty = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		return homeLocationProperty.replaceAll("file:(.*.app).*", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
