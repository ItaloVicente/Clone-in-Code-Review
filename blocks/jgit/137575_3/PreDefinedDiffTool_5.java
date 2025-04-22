
package org.eclipse.jgit.diffmergetool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectStream;

public class FileElement {

	private final String path;

	private final String id;

	private ObjectStream stream;

	private File tempFile;

	public FileElement(final String path
		this(path
	}

	public FileElement(final String path
			ObjectStream stream) {
		this.path = path;
		this.id = id;
		this.stream = stream;
	}

	public String getPath() {
		return path;
	}

	public String getId() {
		return id;
	}

	public ObjectStream getStream() {
		return stream;
	}

	public void setStream(ObjectStream stream) {
		this.stream = stream;
	}

	public File getFile(File workingDir) throws IOException {
		File file = new File(path);
		String name = file.getName();
		if (path.equals(DiffEntry.DEV_NULL)) { 
			file = new File(workingDir
		}
		else if ((stream != null)) {
			tempFile = File.createTempFile(".__"
			try (OutputStream outStream = new FileOutputStream(tempFile)) {
				int read = 0;
				byte[] bytes = new byte[8 * 1024];
				while ((read = stream.read(bytes)) != -1) {
					outStream.write(bytes
				}
			}
			return tempFile;
		}
		return file;
	}

	public void cleanTemporaries() {
		if (tempFile != null && tempFile.exists())
		tempFile.delete();
	}

}
