package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.jgit.util.SystemReader;

public class JGitPrintWriter extends Writer {

	private final Writer out;

	JGitPrintWriter(Writer out) {
		this.out = out;
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
		out.write(LF);
	}

	private String LF = SystemReader.getInstance().getenv("os.name")
			.equals("Windows") ? "\r\n" : "\n";

	public void print(char value) throws IOException {
		out.write(String.valueOf(value));
	}

	public void print(int value) throws IOException {
		out.write(String.valueOf(value));
	}

	public void print(long value) throws IOException {
		out.write(String.valueOf(value));
	}

	public void print(short value) throws IOException {
		out.write(String.valueOf(value));
	}

	public void format(String fmt
		String s = String.format(fmt
		print(s);
	}

	public void print(Object any) throws IOException {
		print(any.toString());
	}
}
