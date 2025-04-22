package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.urischeme.IOperatingSystemRegistration;

@SuppressWarnings("javadoc")
public class RegistrationLinux implements IOperatingSystemRegistration {

	private static final String PATH_TO_LOCAL_SHARE_APPS = "~/.local/share/applications/"; //$NON-NLS-1$
	private static final String DESKTOP_FILE_EXT = ".desktop"; //$NON-NLS-1$

	private static final String XDG_MIME = "xdg-mime"; //$NON-NLS-1$
	private static final String DEFAULT = "default"; //$NON-NLS-1$
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
	public List<Scheme> getRegisteredSchemes(List<Scheme> schemes) throws IOException {
		List<String> lines = fileProvider.readAllLines(PATH_TO_LOCAL_SHARE_APPS + getDesktopFileName());
		DesktopFileWriter writer = new DesktopFileWriter(lines);

		List<String> schemesStrings = Util.convertSchemes(schemes);

		List<String> registeredSchemesStrings = writer.getRegisteredSchemes(schemesStrings);

		return Util.filter(schemes, registeredSchemesStrings);
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
			return DesktopFileWriter.getMinimalDesktopFileContent();
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

	private String getDesktopFileName() {
		String homeLocationProperty = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
		homeLocationProperty = homeLocationProperty.replaceAll("file:(.*)", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
		homeLocationProperty = homeLocationProperty.replace("/", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		return homeLocationProperty + DESKTOP_FILE_EXT;
	}
}
