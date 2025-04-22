package org.eclipse.urischeme.internal.registration.win32;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.urischeme.registration.impl.IFileProvider;

public class FileProviderMock implements IFileProvider {

	final Map<String, Boolean> fileExistsAnswers = new HashMap<>();
	final Map<String, Boolean> isDirectoryAnswers = new HashMap<>();
	final Map<URL, String> urlTosFilePaths = new HashMap<>();
	final Map<String, Map<String, List<String>>> newDirectoryStreamAnswers = new HashMap<>();

	@Override
	public List<String> readAllLines(String path) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(String path, byte[] bytes) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader newReader(String path) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Writer newWriter(String path) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean fileExists(String path) {
		return fileExistsAnswers.get(path);
	}

	@Override
	public boolean isDirectory(String path) {
		return isDirectoryAnswers.get(path);
	}

	@Override
	public String getFilePath(URL url) {
		return urlTosFilePaths.get(url);
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(String dir, String glob) throws IOException {
		return new DirectoryStream<Path>() {

			@Override
			public void close() throws IOException {
			}

			@Override
			public Iterator<Path> iterator() {
				return newDirectoryStreamAnswers.get(dir).get(glob).stream().map(name -> Paths.get(name)).iterator();
			}
		};
	}

}
