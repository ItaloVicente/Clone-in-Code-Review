package org.eclipse.jgit.lib;

import java.io.File;

import org.eclipse.jgit.util.SystemReader;

public class ConfigUtil {
	public static String pathToString(File file) {
		final String path = file.getPath();
		if (SystemReader.getInstance().isWindows()) {
			return path.replace('\\'
		}
		return path;
	}
}
