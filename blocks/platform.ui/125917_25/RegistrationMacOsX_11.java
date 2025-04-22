package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;
import org.eclipse.urischeme.IUriSchemeExtensionReader.Scheme;

@SuppressWarnings("javadoc")
public class RegistrationLinux implements IOperatingSystemRegistration {

	private static final String USER_HOME = System.getProperty("user.home"); //$NON-NLS-1$
	private static final String PATH_TO_LOCAL_SHARE_APPS = USER_HOME + "/.local/share/applications/"; //$NON-NLS-1$
	private static final String DESKTOP_FILE_EXT = ".desktop"; //$NON-NLS-1$

	private static final String XDG_MIME = "xdg-mime"; //$NON-NLS-1$
	private static final String DEFAULT = "default"; //$NON-NLS-1$
	private static final String QUERY = "query"; //$NON-NLS-1$

	private static final String X_SCHEME_HANDLER_PREFIX = "x-scheme-handler/"; //$NON-NLS-1$

	private IFileProvider fileProvider;
	private IProcessExecutor processExecutor;

	public RegistrationLinux() {
		this(new FileProvider(), new ProcessExecutor());
	}

	public RegistrationLinux(IFileProvider fileProvider, IProcessExecutor processExecutor) {
		this.fileProvider = fileProvider;
		this.processExecutor = processExecutor;
	}

	@Override
	public void handleSchemes(Collection<ISchemeInformation> toAdd, Collection<ISchemeInformation> toRemove)
			throws Exception {
		String desktopFileName = getDesktopFileName();

		changeDesktopFile(toAdd, toRemove, PATH_TO_LOCAL_SHARE_APPS + desktopFileName);

		registerSchemesWithXdgMime(toAdd, desktopFileName);
	}

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<Scheme> schemes) throws Exception {
		List<ISchemeInformation> returnList = new ArrayList<>();

		for (Scheme scheme : schemes) {
			SchemeInformation schemeInfo = new SchemeInformation(scheme.getUriScheme(),
					scheme.getUriSchemeDescription(), null);

			String location = determineHandlerLocation(scheme.getUriScheme());
			if (location.equals(getEclipseExecutableLocation())) {
				schemeInfo.setHandled(true);
			}
			schemeInfo.setHandlerLocation(location); // TODO
			returnList.add(schemeInfo);
		}
		return returnList;
	}

	private String determineHandlerLocation(String uriScheme) throws Exception {
		String desktopFileName = getRegisteredDesktopFileForScheme(uriScheme);
		if (!desktopFileName.isEmpty()) {
			return getHandlerLocationFromDesktopFileIfSchemeRegistered(desktopFileName, uriScheme);
		}
		return ""; //$NON-NLS-1$
	}

	private void changeDesktopFile(Iterable<ISchemeInformation> toAdd, Iterable<ISchemeInformation> toRemove,
			String desktopFilePath) throws IOException {

		List<String> lines = readFileOrGetInitialContent(desktopFilePath);

		DesktopFileWriter writer = new DesktopFileWriter(lines);
		for (ISchemeInformation add : toAdd) {
			writer.addScheme(add.getScheme());
		}
		for (ISchemeInformation remove : toRemove) {
			writer.removeScheme(remove.getScheme());
		}

		fileProvider.write(desktopFilePath, writer.getResult());
	}

	private List<String> readFileOrGetInitialContent(String desktopFilePath) {
		try {
			return fileProvider.readAllLines(desktopFilePath);
		} catch (IOException e) {
			return DesktopFileWriter.getMinimalDesktopFileContent(getEclipseExecutableLocation());
		}
	}

	private void registerSchemesWithXdgMime(Collection<ISchemeInformation> schemes, String desktopFilePath)
			throws Exception {
		if (schemes.isEmpty()) {
			return;
		}
		String scheme = schemes.stream(). //
				map(s -> s.getScheme()). //
				collect(Collectors.joining(" " + X_SCHEME_HANDLER_PREFIX, X_SCHEME_HANDLER_PREFIX, "")); //$NON-NLS-1$ //$NON-NLS-2$
		processExecutor.execute(XDG_MIME, DEFAULT, desktopFilePath, scheme);
	}

	private String getHandlerLocationFromDesktopFileIfSchemeRegistered(String desktopFileName, String scheme)
			throws IOException {
		List<String> lines = fileProvider.readAllLines(PATH_TO_LOCAL_SHARE_APPS + desktopFileName);
		DesktopFileWriter writer = new DesktopFileWriter(lines);
		if (writer.isRegistered(scheme)) {
			return writer.getExecutableLocation();
		}
		return ""; //$NON-NLS-1$
	}

	private String getRegisteredDesktopFileForScheme(String scheme) throws Exception {
		return processExecutor.execute(XDG_MIME, QUERY, DEFAULT, X_SCHEME_HANDLER_PREFIX + scheme);
	}

	private String getDesktopFileName() {
		String homeLocationProperty = getEclipseHomeLocation();
		homeLocationProperty = homeLocationProperty.replace("/", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		return homeLocationProperty + DESKTOP_FILE_EXT;
	}

	private String getEclipseExecutableLocation() {
		String launcher = System.getProperty("eclipse.launcher"); //$NON-NLS-1$
		return launcher;
	}

	private String getEclipseHomeLocation() {
		String homeLocationProperty = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		homeLocationProperty = homeLocationProperty.replaceAll("file:(.*)", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
		return homeLocationProperty;
	}
}
