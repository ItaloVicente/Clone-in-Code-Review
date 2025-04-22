
package org.eclipse.jgit.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.eclipse.jgit.util.SystemReader;

class FileUtil {

	static String readSymlink(File path) throws IOException {
		Path nioPath = path.toPath();
		Path target = Files.readSymbolicLink(nioPath);
		String targetString = target.toString();
		if (SystemReader.getInstance().isWindows())
			targetString = targetString.replace('\\'
		else if (SystemReader.getInstance().isMacOS())
			targetString = Normalizer.normalize(targetString
		return targetString;
	}

	public static void createSymLink(File path
			throws IOException {
		Path nioPath = path.toPath();
		if (Files.exists(nioPath
			Files.delete(nioPath);
		if (SystemReader.getInstance().isWindows())
			target = target.replace('/'
		Path nioTarget = new File(target).toPath();
		Files.createSymbolicLink(nioPath
	}

	public static boolean isSymlink(File path) {
		Path nioPath = path.toPath();
		return Files.isSymbolicLink(nioPath);
	}

	public static long lastModified(File path) throws IOException {
		Path nioPath = path.toPath();
		return Files.getLastModifiedTime(nioPath
				.toMillis();
	}

	public static void setLastModified(File path
		Path nioPath = path.toPath();
		Files.setLastModifiedTime(nioPath
	}

	public static boolean exists(File path) {
		Path nioPath = path.toPath();
		return Files.exists(nioPath
	}

	public static boolean isHidden(File path) throws IOException {
		Path nioPath = path.toPath();
		return Files.isHidden(nioPath);
	}

	public static void setHidden(File path
		Path nioPath = path.toPath();
		Files.setAttribute(nioPath
				LinkOption.NOFOLLOW_LINKS);
	}

	public static long getLength(File path) throws IOException {
		Path nioPath = path.toPath();
		if (Files.isSymbolicLink(nioPath))
			return Files.readSymbolicLink(nioPath).toString().length();
		return Files.size(nioPath);
	}

	public static boolean isDirectory(File path) {
		Path nioPath = path.toPath();
		return Files.isDirectory(nioPath
	}

	public static boolean isFile(File path) {
		Path nioPath = path.toPath();
		return Files.isRegularFile(nioPath
	}

	public static boolean canExecute(File path) {
		if (!isFile(path))
			return false;
		return path.canExecute();
	}

	public static boolean setExecute(File path
		if (!isFile(path))
			return false;
		return path.setExecutable(executable);
	}

}
