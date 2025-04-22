package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.jgit.internal.JGitText;

public abstract class ObjectBuilder {


	private PersonIdent author;

	private GpgSignature gpgSignature;

	private String message;

	private Charset encoding = StandardCharsets.UTF_8;

	protected PersonIdent getAuthor() {
		return author;
	}

	protected void setAuthor(PersonIdent newAuthor) {
		author = Objects.requireNonNull(newAuthor);
	}

	public void setGpgSignature(GpgSignature gpgSignature) {
		this.gpgSignature = gpgSignature;
	}

	public GpgSignature getGpgSignature() {
		return gpgSignature;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(Charset encoding) {
		this.encoding = encoding;
	}

	public abstract byte[] build() throws UnsupportedEncodingException;

	static void writeMultiLineHeader(String in
			boolean enforceAscii)
			throws IOException
		int length = in.length();
		for (int i = 0; i < length; ++i) {
			char ch = in.charAt(i);
			switch (ch) {
			case '\r':
				if (i + 1 < length && in.charAt(i + 1) == '\n') {
					++i;
				}
				if (i + 1 < length) {
					out.write('\n');
					out.write(' ');
				}
				break;
			case '\n':
				if (i + 1 < length) {
					out.write('\n');
					out.write(' ');
				}
				break;
			default:
				if (ch > 127 && enforceAscii)
					throw new IllegalArgumentException(MessageFormat
							.format(JGitText.get().notASCIIString
				out.write(ch);
				break;
			}
		}
	}
}
