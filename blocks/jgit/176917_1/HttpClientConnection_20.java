package org.eclipse.jgit.gpg.bc.internal.keys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Arrays;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.operator.PBEProtectionRemoverFactory;
import org.bouncycastle.openpgp.operator.PGPDigestCalculatorProvider;
import org.bouncycastle.openpgp.operator.jcajce.JcePBEProtectionRemoverFactory;
import org.bouncycastle.util.io.Streams;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.gpg.bc.internal.BCText;
import org.eclipse.jgit.util.RawParseUtils;

public final class SecretKeys {

	private SecretKeys() {
	}

	public interface PassphraseSupplier {

		char[] getPassphrase() throws PGPException
				UnsupportedCredentialItem
	}

			.getBytes(StandardCharsets.US_ASCII);

			.getBytes(StandardCharsets.US_ASCII);

	public static PGPSecretKey readSecretKey(InputStream in
			PGPDigestCalculatorProvider calculatorProvider
			PassphraseSupplier passphraseSupplier
			throws IOException
			UnsupportedCredentialItem
		byte[] data = Streams.readAll(in);
		if (data.length == 0) {
			throw new EOFException();
		} else if (data.length < 4 + PROTECTED_KEY.length) {
			throw new IOException(
					MessageFormat.format(BCText.get().secretKeyTooShort
							Integer.toUnsignedString(data.length)));
		}
		SExprParser parser = new SExprParser(calculatorProvider);
		byte firstChar = data[0];
		try {
			if (firstChar == '(') {
				PBEProtectionRemoverFactory decryptor = null;
				if (matches(data
					decryptor = new JcePBEProtectionRemoverFactory(
							passphraseSupplier.getPassphrase()
							calculatorProvider);
				}
				try (InputStream sIn = new ByteArrayInputStream(data)) {
					return parser.parseSecretKey(sIn
				}
			}
			try (ByteArrayInputStream keyIn = new ByteArrayInputStream(data)) {
				byte[] rawData = keyFromNameValueFormat(keyIn);
				if (!matches(rawData
					try (InputStream sIn = new ByteArrayInputStream(
							convertSexpression(rawData))) {
						return parser.parseSecretKey(sIn
					}
				}
				boolean isOCB[] = { false };
				byte[] sExp = convertSexpression(rawData
				PBEProtectionRemoverFactory decryptor;
				if (isOCB[0]) {
					decryptor = new OCBPBEProtectionRemoverFactory(
							passphraseSupplier.getPassphrase()
							calculatorProvider
				} else {
					decryptor = new JcePBEProtectionRemoverFactory(
							passphraseSupplier.getPassphrase()
							calculatorProvider);
				}
				try (InputStream sIn = new ByteArrayInputStream(sExp)) {
					return parser.parseSecretKey(sIn
				}
			}
		} catch (IOException e) {
			throw new PGPException(e.getLocalizedMessage()
		}
	}

	private static byte[] getAad(byte[] sExp) {
		while (sExp[i] != '(') {
			i++;
		}
		int aadStart = i++;
		int aadEnd = skip(sExp
				.getBytes(StandardCharsets.US_ASCII);
		while (!matches(sExp
			i++;
		}
		int protectedStart = i;
		int protectedEnd = skip(sExp
		byte[] aadData = new byte[aadEnd - aadStart
				- (protectedEnd - protectedStart)];
		System.arraycopy(sExp
		System.arraycopy(sExp
				aadEnd - protectedEnd);
		return aadData;
	}

	private static int skip(byte[] sExp
		int i = start + 1;
		int depth = 1;
		while (depth > 0) {
			switch (sExp[i]) {
			case '(':
				depth++;
				break;
			case ')':
				depth--;
				break;
			default:
				int j = i;
				while (sExp[j] >= '0' && sExp[j] <= '9') {
					j++;
				}
				int length = Integer.parseInt(
						new String(sExp
				i = j + length;
			}
			i++;
		}
		return i;
	}

	private static boolean matches(byte[] src
		if (from < 0 || from + needle.length > src.length) {
			return false;
		}
		return org.bouncycastle.util.Arrays.constantTimeAreEqual(needle.length
				src
	}

	private static byte[] convertSexpression(byte[] humanForm)
			throws IOException {
		boolean[] isOCB = { false };
		return convertSexpression(humanForm
	}

	private static byte[] convertSexpression(byte[] humanForm
			throws IOException {
		int pos = 0;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(
				humanForm.length)) {
			while (pos < humanForm.length) {
				byte b = humanForm[pos];
				if (b == '(' || b == ')') {
					out.write(b);
					pos++;
				} else if (isGpgSpace(b)) {
					pos++;
				} else if (b == '#') {
					int i = ++pos;
					while (i < humanForm.length && isHex(humanForm[i])) {
						i++;
					}
					if (i == pos || humanForm[i] != '#') {
						throw new StreamCorruptedException(
								BCText.get().sexprHexNotClosed);
					}
					if ((i - pos) % 2 != 0) {
						throw new StreamCorruptedException(
								BCText.get().sexprHexOdd);
					}
					int l = (i - pos) / 2;
					out.write(Integer.toString(l)
							.getBytes(StandardCharsets.US_ASCII));
					out.write(':');
					while (pos < i) {
						int x = (nibble(humanForm[pos]) << 4)
								| nibble(humanForm[pos + 1]);
						pos += 2;
						out.write(x);
					}
					pos = i + 1;
				} else if (isTokenChar(b)) {
					int start = pos++;
					while (pos < humanForm.length
							&& isTokenChar(humanForm[pos])) {
						pos++;
					}
					int l = pos - start;
					if (pos - start == OCB_PROTECTED.length
							&& matches(humanForm
						isOCB[0] = true;
					}
					out.write(Integer.toString(l)
							.getBytes(StandardCharsets.US_ASCII));
					out.write(':');
					out.write(humanForm
				} else if (b == '"') {
					int start = ++pos;
					boolean escaped = false;
					while (pos < humanForm.length
							&& (escaped || humanForm[pos] != '"')) {
						int ch = humanForm[pos++];
						escaped = !escaped && ch == '\\';
					}
					if (pos >= humanForm.length) {
						throw new StreamCorruptedException(
								BCText.get().sexprStringNotClosed);
					}
					byte[] dq = dequote(humanForm
					out.write(Integer.toString(dq.length)
							.getBytes(StandardCharsets.US_ASCII));
					out.write(':');
					out.write(dq);
					pos++;
				} else {
					throw new StreamCorruptedException(
							MessageFormat.format(BCText.get().sexprUnhandled
									Integer.toHexString(b & 0xFF)));
				}
			}
			return out.toByteArray();
		}
	}

	private static byte[] dequote(byte[] in
			throws StreamCorruptedException {
		byte[] out = new byte[to - from];
		int j = 0;
		int i = from;
		while (i < to) {
			byte b = in[i++];
			if (b != '\\') {
				out[j++] = b;
				continue;
			}
			if (i == to) {
				throw new StreamCorruptedException(
						BCText.get().sexprStringInvalidEscapeAtEnd);
			}
			b = in[i++];
			switch (b) {
			case 'b':
				out[j++] = '\b';
				break;
			case 'f':
				out[j++] = '\f';
				break;
			case 'n':
				out[j++] = '\n';
				break;
			case 'r':
				out[j++] = '\r';
				break;
			case 't':
				out[j++] = '\t';
				break;
			case 'v':
				out[j++] = 0x0B;
				break;
			case '"':
			case '\'':
			case '\\':
				out[j++] = b;
				break;
			case '\r':
				if (i < to && in[i] == '\n') {
					i++;
				}
				break;
			case '\n':
				if (i < to && in[i] == '\r') {
					i++;
				}
				break;
			case 'x':
				if (i + 1 >= to || !isHex(in[i]) || !isHex(in[i + 1])) {
					throw new StreamCorruptedException(
							BCText.get().sexprStringInvalidHexEscape);
				}
				out[j++] = (byte) ((nibble(in[i]) << 4) | nibble(in[i + 1]));
				i += 2;
				break;
			case '0':
			case '1':
			case '2':
			case '3':
				if (i + 2 >= to || !isOctal(in[i]) || !isOctal(in[i + 1])
						|| !isOctal(in[i + 2])) {
					throw new StreamCorruptedException(
							BCText.get().sexprStringInvalidOctalEscape);
				}
				out[j++] = (byte) (((((in[i] - '0') << 3)
						| (in[i + 1] - '0')) << 3) | (in[i + 2] - '0'));
				i += 3;
				break;
			default:
				throw new StreamCorruptedException(MessageFormat.format(
						BCText.get().sexprStringInvalidEscape
						Integer.toHexString(b & 0xFF)));
			}
		}
		return Arrays.copyOf(out
	}

	static byte[] keyFromNameValueFormat(InputStream in) throws IOException {
		int[] nameLow = { 'k'
		int[] nameCap = { 'K'
		int nameIdx = 0;
		for (;;) {
			int next = in.read();
			if (next < 0) {
				throw new EOFException();
			}
			if (next == '\n') {
				nameIdx = 0;
			} else if (nameIdx >= 0) {
				if (nameLow[nameIdx] == next || nameCap[nameIdx] == next) {
					nameIdx++;
					if (nameIdx == nameLow.length) {
						break;
					}
				} else {
					nameIdx = -1;
				}
			}
		}
		int last = ':';
		byte[] rawData;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(8192)) {
			for (;;) {
				int next = in.read();
				if (next < 0) {
					break;
				}
				if (last == '\n') {
					if (next == ' ' || next == '\t') {
						last = next;
						continue;
					}
				}
				out.write(next);
				last = next;
			}
			rawData = out.toByteArray();
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(
				rawData.length)) {
			int lineStart = 0;
			boolean trimLeading = true;
			while (lineStart < rawData.length) {
				int nextLineStart = RawParseUtils.nextLF(rawData
				if (trimLeading) {
					while (lineStart < nextLineStart
							&& isGpgSpace(rawData[lineStart])) {
						lineStart++;
					}
				}
				int i = nextLineStart - 1;
				while (lineStart < i && isGpgSpace(rawData[i])) {
					i--;
				}
				if (i <= lineStart) {
					out.write('\n');
					trimLeading = true;
				} else {
					out.write(rawData
					trimLeading = false;
				}
				lineStart = nextLineStart;
			}
			return out.toByteArray();
		}
	}

	private static boolean isGpgSpace(int ch) {
		return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
	}

	private static boolean isTokenChar(int ch) {
		switch (ch) {
		case '-':
		case '.':
		case '/':
		case '_':
		case ':':
		case '*':
		case '+':
		case '=':
			return true;
		default:
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= '0' && ch <= '9')) {
				return true;
			}
			return false;
		}
	}

	private static boolean isHex(int ch) {
		return (ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F')
				|| (ch >= 'a' && ch <= 'f');
	}

	private static boolean isOctal(int ch) {
		return (ch >= '0' && ch <= '7');
	}

	private static int nibble(int ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'A' && ch <= 'F') {
			return ch - 'A' + 10;
		} else if (ch >= 'a' && ch <= 'f') {
			return ch - 'a' + 10;
		}
		return -1;
	}
}
