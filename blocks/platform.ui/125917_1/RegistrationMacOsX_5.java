package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.List;

import org.eclipse.urischeme.IOperatingSystemRegistration;


@SuppressWarnings("javadoc")
public class RegistrationLinux implements IOperatingSystemRegistration {

	private static final String ECLIPSE_DESKTOP = "eclipse.desktop"; //$NON-NLS-1$
	private static final String PATH_TO_DESKTOP_FILE = "~/.local/share/applications/" + ECLIPSE_DESKTOP; //$NON-NLS-1$
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
	public void handleSchemes(Iterable<Scheme> toAdd, Iterable<Scheme> toRemove) throws IOException {
		changeDesktopFile(toAdd, toRemove);

		registerSchemesWithXdgMime(toAdd);
	}

	private void changeDesktopFile(Iterable<Scheme> toAdd, Iterable<Scheme> toRemove) throws IOException {
		List<String> lines = fileProvider.readAllLines(PATH_TO_DESKTOP_FILE);

		DesktopFileWriter writer = new DesktopFileWriter(lines);
		for (Scheme add : toAdd) {
			writer.addScheme(add.getScheme());
		}
		for (Scheme remove : toRemove) {
			writer.removeScheme(remove.getScheme());
		}

		fileProvider.write(PATH_TO_DESKTOP_FILE, writer.getResult());
	}

	private void registerSchemesWithXdgMime(Iterable<Scheme> schemes) throws IOException {
		for (Scheme scheme : schemes) {
			processExecutor.execute(XDG_MIME, DEFAULT, PATH_TO_DESKTOP_FILE,
					X_SCHEME_HANDLER_PREFIX + scheme.getScheme());
		}
	}
}
