package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;

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
	public void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove)
			throws Exception {
		String pathToEclipseApp = getPathToEclipseApp();

		changePlistFile(toAdd, toRemove, pathToEclipseApp);

		registerAppWithLsregister(pathToEclipseApp);
	}

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<Scheme> schemes) throws IOException {
		PlistFileWriter writer = getPlistFileWriter(getPathToEclipseApp() + PLIST_PATH_SUFFIX);

		Function<Scheme, ISchemeInformation> toSchemeInfo = new Function<Scheme, ISchemeInformation>() {

			@Override
			public ISchemeInformation apply(Scheme i) {
				SchemeInformation schemeInfo = new SchemeInformation(i.getUriScheme(), i.getUriSchemeDescription(),
						null);
				schemeInfo.setHandled(writer.isRegisteredScheme(schemeInfo.getScheme()));
				return schemeInfo;
			}
		};

		return schemes.stream().map(toSchemeInfo).collect(Collectors.toList());
	}

	private PlistFileWriter getPlistFileWriter(String plistPath) throws IOException {
		return new PlistFileWriter(fileProvider.newReader(plistPath));
	}

	private void registerAppWithLsregister(String pathToEclipseApp) throws Exception {
		processExecutor.execute(LSREGISTER, UNREGISTER, pathToEclipseApp);
		processExecutor.execute(LSREGISTER, RECURSIVE, pathToEclipseApp);
	}

	private void changePlistFile(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove,
			String pathToEclipseApp) throws IOException {
		String plistPath = pathToEclipseApp + PLIST_PATH_SUFFIX;

		PlistFileWriter writer = getPlistFileWriter(plistPath);

		for (ISchemeInformation scheme : toAdd) {
			writer.addScheme(scheme.getScheme(), scheme.getDescription());
		}

		for (ISchemeInformation scheme : toRemove) {
			writer.removeScheme(scheme.getScheme());
		}

		writer.writeTo(fileProvider.newWriter(plistPath));
	}

	private String getPathToEclipseApp() {
		String homeLocationProperty = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		return homeLocationProperty.replaceAll("file:(.*.app).*", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
