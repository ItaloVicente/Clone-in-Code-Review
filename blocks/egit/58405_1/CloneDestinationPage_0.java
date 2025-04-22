package org.eclipse.egit.ui.internal;

import java.io.File;
import java.io.IOException;

public final class FileUtils {

	private FileUtils() {
	}

	public static File canonical(File file) {
		if (file == null) {
			return null;
		}
		try {
			return file.getCanonicalFile();
		} catch (IOException e) {
			return file;
		}
	}
}
