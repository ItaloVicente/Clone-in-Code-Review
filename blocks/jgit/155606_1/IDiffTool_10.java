
package org.eclipse.jgit.diffmergetool;

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
		BACKUP();
	}

	private final String path;

	private final Type type;

	private final File workDir;

	private InputStream stream;

	private File tempFile;

	public FileElement(final String path
		this(path
	}

	public FileElement(final String path
		this(path
	}

	public FileElement(final String path
			InputStream stream) {
		this.path = path;
		this.type = type;
		this.workDir = workDir;
		this.stream = stream;
	}

	public String getPath() {
		return path;
	}

	public Type getType() {
		return type;
	}

	public File getFile() throws IOException {
		if ((tempFile != null) && (stream == null)) {
			return tempFile;
		}
		File file = new File(workDir
		if ((stream != null) || isNullPath()) {
			if (tempFile == null) {
				tempFile = getTempFile(file
			}
			if (stream != null) {
				copyFromStream(tempFile
			}
			stream = null;
			return tempFile;
		}
		return file;
	}

	public boolean isNullPath() {
		return path.equals(DiffEntry.DEV_NULL);
	}

	public File createTempFile(final File workingDir) throws IOException {
		if (tempFile == null) {
			tempFile = getTempFile(new File(path)
		}
		return tempFile;
	}

	public void cleanTemporaries() {
		if (tempFile != null && tempFile.exists()) {
			tempFile.delete();
		}
		tempFile = null;
	}

	public String replaceVariable(String input) throws IOException {
		return input.replace("$" + type.name()
	}

	public void addToEnv(Map<String
		env.put(type.name()
	}

	private static File getTempFile(final File file
			final File workingDir) throws IOException {
		String[] fileNameAndExtension = splitBaseFileNameAndExtension(file);
		return File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_"
				fileNameAndExtension[1]
	}

	private static void copyFromStream(final File file
			final InputStream stream)
			throws IOException
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

	private static String[] splitBaseFileNameAndExtension(File file) {
		String[] result = new String[2];
		result[0] = file.getName();
		if (idx > 0) {
			result[1] = result[0].substring(idx
			result[0] = result[0].substring(0
		}
		return result;
	}

}
