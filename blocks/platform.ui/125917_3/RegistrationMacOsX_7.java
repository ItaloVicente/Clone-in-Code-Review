package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
	public void handleSchemes(Collection<Scheme> toAdd, Collection<Scheme> toRemove) throws IOException {
		String pathToEclipseApp = getPathToEclipseApp();

		changePlistFile(toAdd, toRemove, pathToEclipseApp);

		registerAppWithLsregister(pathToEclipseApp);
	}

	@Override
	public List<Scheme> getRegisteredSchemes(List<Scheme> schemes) throws IOException {
		PlistFileWriter writer = getPlistFileWriter(getPathToEclipseApp() + PLIST_PATH_SUFFIX);

		List<String> schemesStrings = Util.convertSchemes(schemes);

		List<String> registeredSchemesStrings = writer.getRegisteredSchemes(schemesStrings);

		return Util.filter(schemes, registeredSchemesStrings);
	}

	private PlistFileWriter getPlistFileWriter(String plistPath) throws IOException {
		return new PlistFileWriter(fileProvider.newReader(plistPath));
	}

	private void registerAppWithLsregister(String pathToEclipseApp) throws IOException {
		processExecutor.execute(LSREGISTER, UNREGISTER, pathToEclipseApp);
		processExecutor.execute(LSREGISTER, RECURSIVE, pathToEclipseApp);
	}

	private void changePlistFile(Collection<Scheme> toAdd, Collection<Scheme> toRemove, String pathToEclipseApp)
			throws IOException {
		String plistPath = pathToEclipseApp + PLIST_PATH_SUFFIX;

		PlistFileWriter writer = getPlistFileWriter(plistPath);

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
