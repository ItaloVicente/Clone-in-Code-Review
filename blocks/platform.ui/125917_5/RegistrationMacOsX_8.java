package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.urischeme.IOperatingSystemRegistration;
import org.eclipse.urischeme.ISchemeInformation;

@SuppressWarnings("javadoc")
public class RegistrationLinux implements IOperatingSystemRegistration {

	private static final String PATH_TO_LOCAL_SHARE_APPS = "~/.local/share/applications/"; //$NON-NLS-1$
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
	public void handleSchemes(Collection<Scheme> toAdd, Collection<Scheme> toRemove) throws IOException {
		String desktopFileName = getDesktopFileName();

		changeDesktopFile(toAdd, toRemove, PATH_TO_LOCAL_SHARE_APPS + desktopFileName);

		registerSchemesWithXdgMime(toAdd, desktopFileName);
	}

	@Override
	public List<ISchemeInformation> getSchemesInformation(Collection<Scheme> schemes) throws IOException {
		Map<String, String> desktopFileNameToEclipseLocation = new HashMap<>();

		List<String> lines = fileProvider.readAllLines(PATH_TO_LOCAL_SHARE_APPS + getDesktopFileName());
		DesktopFileWriter writer = new DesktopFileWriter(lines);

		Consumer<SchemeInformation> fillInformation = new Consumer<SchemeInformation>() {

			@Override
			public void accept(SchemeInformation info) {
				info.setHandled(writer.isRegistered(info.getScheme()));
				if (info.isHandled()) {
					return;
				}
				try {
					String desktopFileName = getRegisteredDesktopFileForScheme(info.getScheme());
					if (!desktopFileName.isEmpty()) {
						String eclipseLocation = desktopFileNameToEclipseLocation.get(desktopFileName);
						if (eclipseLocation == null) {
							eclipseLocation = getEclipseExecutableLocationFromDesktopFileIfSchemeRegistered(
									desktopFileName, info.getScheme());

							desktopFileNameToEclipseLocation.put(desktopFileName, eclipseLocation);
						}
						info.setHandlerInstanceLocation(eclipseLocation);
					}
				} catch (IOException e) {
				}
			}
		};

		return schemes.stream(). //
				map(s -> new SchemeInformation(s.getScheme())). //
				peek(fillInformation). //
				collect(Collectors.toList());

	}

	private void changeDesktopFile(Iterable<Scheme> toAdd, Iterable<Scheme> toRemove, String desktopFilePath)
			throws IOException {

		List<String> lines = readFileOrGetInitialContent(desktopFilePath);

		DesktopFileWriter writer = new DesktopFileWriter(lines);
		for (Scheme add : toAdd) {
			writer.addScheme(add.getScheme());
		}
		for (Scheme remove : toRemove) {
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

	private void registerSchemesWithXdgMime(Collection<Scheme> schemes, String desktopFilePath) throws IOException {
		if (schemes.isEmpty()) {
			return;
		}
		String scheme = schemes.stream(). //
				map(s -> s.getScheme()). //
				collect(Collectors.joining(" " + X_SCHEME_HANDLER_PREFIX, X_SCHEME_HANDLER_PREFIX, "")); //$NON-NLS-1$ //$NON-NLS-2$
		processExecutor.execute(XDG_MIME, DEFAULT, desktopFilePath, scheme);
	}

	private String getEclipseExecutableLocationFromDesktopFileIfSchemeRegistered(String desktopFileName, String scheme)
			throws IOException {
		List<String> lines = fileProvider.readAllLines(PATH_TO_LOCAL_SHARE_APPS + desktopFileName);
		DesktopFileWriter writer = new DesktopFileWriter(lines);
		if (writer.isRegistered(scheme)) {
			return writer.getEclipseExecutableLocation();
		}
		return ""; //$NON-NLS-1$
	}

	private String getRegisteredDesktopFileForScheme(String scheme) throws IOException {
		return processExecutor.execute(XDG_MIME, QUERY, DEFAULT, X_SCHEME_HANDLER_PREFIX + scheme);
	}

	private String getDesktopFileName() {
		String homeLocationProperty = getEclipseHomeLocation();
		homeLocationProperty = homeLocationProperty.replace("/", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		return homeLocationProperty + DESKTOP_FILE_EXT;
	}

	private String getEclipseExecutableLocation() {
		String location = getEclipseHomeLocation();
		String launcher = System.getProperty("eclipse.launcher"); //$NON-NLS-1$
		return location + launcher;
	}

	private String getEclipseHomeLocation() {
		String homeLocationProperty = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		homeLocationProperty = homeLocationProperty.replaceAll("file:(.*)", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
		return homeLocationProperty;
	}
}
