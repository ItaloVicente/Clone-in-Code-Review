package org.eclipse.ui.internal.wizards.datatransfer;

import java.io.File;

public class CouldNotImportProjectException extends Exception {

	private static final long serialVersionUID = 5207202862271299462L;
	private File location;

	public CouldNotImportProjectException(File location, Exception cause) {
		super("Could not import project located at " + location.getAbsolutePath(), cause); //$NON-NLS-1$
		this.location = location;
	}

	public CouldNotImportProjectException(File location, String message) {
		super(message);
		this.location = location;
	}

	public File getLocation() {
		return this.location;
	}
}
