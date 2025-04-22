package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.References;

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

	public void setGpgSignature(@Nullable GpgSignature gpgSignature) {
		this.gpgSignature = gpgSignature;
	}

	@Nullable
	public GpgSignature getGpgSignature() {
		return gpgSignature;
	}

	@Nullable
	public String getMessage() {
		return message;
	}

	public void setMessage(@Nullable String message) {
		this.message = message;
	}

	@NonNull
	public Charset getEncoding() {
		return encoding;
	}

	public void setEncoding(@NonNull Charset encoding) {
		this.encoding = encoding;
	}

	@NonNull
	public abstract byte[] build() throws UnsupportedEncodingException;

	static void writeMultiLineHeader(@NonNull String in
			@NonNull OutputStream out
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

	static void writeEncoding(@NonNull Charset encoding
			@NonNull OutputStream out) throws IOException {
		if (!References.isSameObject(encoding
			out.write(hencoding);
			out.write(' ');
			out.write(Constants.encodeASCII(encoding.name()));
			out.write('\n');
		}
	}
}
