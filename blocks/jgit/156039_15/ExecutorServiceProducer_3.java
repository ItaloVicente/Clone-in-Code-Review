package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.util.FileUtils;

public final class IOUtil {

	protected final List<File> tempFiles = new ArrayList<>();

	public void cleanup() {
		for (final File tempFile : tempFiles) {
			try {
				FileUtils.delete(tempFile
			} catch (IOException e) {
			}
		}
	}

	public File createTempDirectory() throws IOException {
		final File temp = File.createTempFile("temp"
		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		tempFiles.add(temp);

		return temp;
	}
}
