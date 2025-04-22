package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.Writer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.eclipse.jgit.util.SystemReader;

public class ThrowingPrintWriter extends Writer {

	private final Writer out;

	private final String LF;

	public ThrowingPrintWriter(Writer out) {
		this.out = out;
		LF = AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
			}
		});
	}

	@Override
	public void write(char[] cbuf
		out.write(cbuf
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}

	public void println(String s) throws IOException {
		print(s + LF);
	}

	public void println() throws IOException {
		print(LF);
	}

	public void print(char value) throws IOException {
		print(String.valueOf(value));
	}

	public void print(int value) throws IOException {
		print(String.valueOf(value));
	}

	public void print(long value) throws IOException {
		print(String.valueOf(value));
	}

	public void print(short value) throws IOException {
		print(String.valueOf(value));
	}

	public void format(String fmt
		print(String.format(fmt
	}

	public void print(Object any) throws IOException {
		out.write(String.valueOf(any));
	}
}
