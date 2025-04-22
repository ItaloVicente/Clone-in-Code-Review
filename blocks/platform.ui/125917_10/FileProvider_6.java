package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileProvider implements IFileProvider {

	@Override
	public List<String> readAllLines(String path) throws IOException {
		Path filePath = Paths.get(path);
		return Files.readAllLines(filePath);
	}

	@Override
	public void write(String path, byte[] bytes) throws IOException {
		Path filePath = Paths.get(path);
		Files.write(filePath, bytes);
	}

	@Override
	public Reader newReader(String path) throws IOException {
		Path filePath = Paths.get(path);
		return Files.newBufferedReader(filePath);
	}

	@Override
	public Writer newWriter(String path) throws IOException {
		Path filePath = Paths.get(path);
		return Files.newBufferedWriter(filePath);
	}
}
