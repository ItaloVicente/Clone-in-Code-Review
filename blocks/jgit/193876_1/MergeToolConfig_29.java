
package org.eclipse.jgit.internal.diffmergetool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;

public class FileElement {

	public enum Type {
		LOCAL
		REMOTE
		MERGED
		BASE
		BACKUP
	}

	private final String path;

	private final Type type;

	private InputStream stream;

	private File tempFile;

	public FileElement(String path
		this(path
	}

	public FileElement(String path
			InputStream stream) {
		this.path = path;
		this.type = type;
		this.tempFile = tempFile;
		this.stream = stream;
	}

	public String getPath() {
		return path;
	}

	public Type getType() {
		return type;
	}

	public File getFile(File directory
		if ((tempFile != null) && (stream == null)) {
			return tempFile;
		}
		tempFile = getTempFile(path
		return copyFromStream(tempFile
	}

	public File getFile() throws IOException {
		if ((tempFile != null) && (stream == null)) {
			return tempFile;
		}
		File file = new File(path);
		if ((stream != null) || isNullPath()) {
			tempFile = getTempFile(file);
			return copyFromStream(tempFile
		}
		return file;
	}

	public boolean isNullPath() {
		return path.equals(DiffEntry.DEV_NULL);
	}

	public File createTempFile(File directory) throws IOException {
		if (tempFile == null) {
			File file = new File(path);
			if (directory != null) {
				tempFile = getTempFile(file
			} else {
				tempFile = getTempFile(file);
			}
		}
		return tempFile;
	}

	private static File getTempFile(File file) throws IOException {
		return File.createTempFile(".__"
	}

	private static File getTempFile(File file
			throws IOException {
		String[] fileNameAndExtension = splitBaseFileNameAndExtension(file);
		return File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_"
				fileNameAndExtension[1]
	}

	private static File getTempFile(String path
			throws IOException {
		return getTempFile(new File(path)
	}

	public void cleanTemporaries() {
		if (tempFile != null && tempFile.exists())
		tempFile.delete();
		tempFile = null;
	}

	private static File copyFromStream(File file
			throws IOException
		if (stream != null) {
			try (OutputStream outStream = new FileOutputStream(file)) {
				int read = 0;
				byte[] bytes = new byte[8 * 1024];
				while ((read = stream.read(bytes)) != -1) {
					outStream.write(bytes
				}
			} finally {
				stream.close();
			}
		}
		return file;
	}

	private static String[] splitBaseFileNameAndExtension(File file) {
		String[] result = new String[2];
		result[0] = file.getName();
		if (idx > 0) {
			result[1] = result[0].substring(idx
			result[0] = result[0].substring(0
		}
		return result;
	}

	public String replaceVariable(String input) throws IOException {
		return input.replace("$" + type.name()
	}

	public void addToEnv(Map<String
		env.put(type.name()
	}

}
