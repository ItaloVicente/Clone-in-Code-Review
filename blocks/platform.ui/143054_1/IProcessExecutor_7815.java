package org.eclipse.urischeme.internal.registration;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.eclipse.osgi.service.datalocation.Location;

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

	@SuppressWarnings("javadoc")
	DirectoryStream<Path> newDirectoryStream(String path, String glob) throws IOException;

	String getFilePath(URL url);

}
