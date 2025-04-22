package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.List;

public interface IFileProvider {

	@SuppressWarnings("javadoc")
	List<String> readAllLines(String path) throws IOException;

	@SuppressWarnings("javadoc")
	void write(String path, byte[] bytes) throws IOException;

	@SuppressWarnings("javadoc")
	Reader newReader(String path) throws IOException;

	@SuppressWarnings("javadoc")
	Writer newWriter(String path) throws IOException;

	@SuppressWarnings("javadoc")
	boolean fileExists(String path);

	@SuppressWarnings("javadoc")
	boolean isDirectory(String path);
}
