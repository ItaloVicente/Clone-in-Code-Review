package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

public class FileProviderMock implements IFileProvider {

	String readPath = "not/read";
	String readException = null;
	String writePath = "not/written";

	List<String> lines;
	Reader reader;
	byte[] writtenBytes;
	Writer writer;

	@Override
	public List<String> readAllLines(String path) throws IOException {
		if (readException != null) {
			String text = readException;
			readException = null; // throw only once
			throw new IOException(text);
		}
		this.readPath = path;
		return lines;
	}

	@Override
	public void write(String path, byte[] bytes) throws IOException {
		this.writePath = path;
		this.writtenBytes = bytes;
	}

	@Override
	public Reader newReader(String path) throws IOException {
		this.readPath = path;
		return reader;
	}

	@Override
	public Writer newWriter(String path) throws IOException {
		this.writePath = path;
		return this.writer;
	}

}
