
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.ObjectChecker.author;
import static org.eclipse.jgit.lib.ObjectChecker.committer;
import static org.eclipse.jgit.lib.ObjectChecker.encoding;
import static org.eclipse.jgit.lib.ObjectChecker.tagger;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.BinaryBlobException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;

public final class RawParseUtils {
	@Deprecated
	public static final Charset UTF8_CHARSET = UTF_8;

	private static final byte[] digits10;

	private static final byte[] digits16;

	private static final byte[] footerLineKeyChars;

	private static final Map<String

	static {
		encodingAliases = new HashMap<>();
		encodingAliases.put("latin-1"
		encodingAliases.put("iso-latin-1"

		digits10 = new byte['9' + 1];
		Arrays.fill(digits10
		for (char i = '0'; i <= '9'; i++)
			digits10[i] = (byte) (i - '0');

		digits16 = new byte['f' + 1];
		Arrays.fill(digits16
		for (char i = '0'; i <= '9'; i++)
			digits16[i] = (byte) (i - '0');
		for (char i = 'a'; i <= 'f'; i++)
			digits16[i] = (byte) ((i - 'a') + 10);
		for (char i = 'A'; i <= 'F'; i++)
			digits16[i] = (byte) ((i - 'A') + 10);

		footerLineKeyChars = new byte['z' + 1];
		footerLineKeyChars['-'] = 1;
		for (char i = '0'; i <= '9'; i++)
			footerLineKeyChars[i] = 1;
		for (char i = 'A'; i <= 'Z'; i++)
			footerLineKeyChars[i] = 1;
		for (char i = 'a'; i <= 'z'; i++)
			footerLineKeyChars[i] = 1;
	}

	public static final int match(byte[] b
		if (ptr + src.length > b.length)
			return -1;
		for (int i = 0; i < src.length; i++
			if (b[ptr] != src[i])
				return -1;
		return ptr;
	}

	private static final byte[] base10byte = { '0'
			'6'

	public static int formatBase10(final byte[] b
		if (value == 0) {
			b[--o] = '0';
			return o;
		}
		final boolean isneg = value < 0;
		if (isneg)
			value = -value;
		while (value != 0) {
			b[--o] = base10byte[value % 10];
			value /= 10;
		}
		if (isneg)
			b[--o] = '-';
		return o;
	}

	public static final int parseBase10(final byte[] b
			final MutableInteger ptrResult) {
		int r = 0;
		int sign = 0;
		try {
			final int sz = b.length;
			while (ptr < sz && b[ptr] == ' ')
				ptr++;
			if (ptr >= sz)
				return 0;

			switch (b[ptr]) {
			case '-':
				sign = -1;
				ptr++;
				break;
			case '+':
				ptr++;
				break;
			}

			while (ptr < sz) {
				final byte v = digits10[b[ptr]];
				if (v < 0)
					break;
				r = (r * 10) + v;
				ptr++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		if (ptrResult != null)
			ptrResult.value = ptr;
		return sign < 0 ? -r : r;
	}

	public static final long parseLongBase10(final byte[] b
			final MutableInteger ptrResult) {
		long r = 0;
		int sign = 0;
		try {
			final int sz = b.length;
			while (ptr < sz && b[ptr] == ' ')
				ptr++;
			if (ptr >= sz)
				return 0;

			switch (b[ptr]) {
			case '-':
				sign = -1;
				ptr++;
				break;
			case '+':
				ptr++;
				break;
			}

			while (ptr < sz) {
				final byte v = digits10[b[ptr]];
				if (v < 0)
					break;
				r = (r * 10) + v;
				ptr++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		if (ptrResult != null)
			ptrResult.value = ptr;
		return sign < 0 ? -r : r;
	}

	public static final int parseHexInt16(final byte[] bs
		int r = digits16[bs[p]] << 4;

		r |= digits16[bs[p + 1]];
		r <<= 4;

		r |= digits16[bs[p + 2]];
		r <<= 4;

		r |= digits16[bs[p + 3]];
		if (r < 0)
			throw new ArrayIndexOutOfBoundsException();
		return r;
	}

	public static final int parseHexInt32(final byte[] bs
		int r = digits16[bs[p]] << 4;

		r |= digits16[bs[p + 1]];
		r <<= 4;

		r |= digits16[bs[p + 2]];
		r <<= 4;

		r |= digits16[bs[p + 3]];
		r <<= 4;

		r |= digits16[bs[p + 4]];
		r <<= 4;

		r |= digits16[bs[p + 5]];
		r <<= 4;

		r |= digits16[bs[p + 6]];

		final int last = digits16[bs[p + 7]];
		if (r < 0 || last < 0)
			throw new ArrayIndexOutOfBoundsException();
		return (r << 4) | last;
	}

	public static final long parseHexInt64(final byte[] bs
		long r = digits16[bs[p]] << 4;

		r |= digits16[bs[p + 1]];
		r <<= 4;

		r |= digits16[bs[p + 2]];
		r <<= 4;

		r |= digits16[bs[p + 3]];
		r <<= 4;

		r |= digits16[bs[p + 4]];
		r <<= 4;

		r |= digits16[bs[p + 5]];
		r <<= 4;

		r |= digits16[bs[p + 6]];
		r <<= 4;

		r |= digits16[bs[p + 7]];
		r <<= 4;

		r |= digits16[bs[p + 8]];
		r <<= 4;

		r |= digits16[bs[p + 9]];
		r <<= 4;

		r |= digits16[bs[p + 10]];
		r <<= 4;

		r |= digits16[bs[p + 11]];
		r <<= 4;

		r |= digits16[bs[p + 12]];
		r <<= 4;

		r |= digits16[bs[p + 13]];
		r <<= 4;

		r |= digits16[bs[p + 14]];

		final int last = digits16[bs[p + 15]];
		if (r < 0 || last < 0)
			throw new ArrayIndexOutOfBoundsException();
		return (r << 4) | last;
	}

	public static final int parseHexInt4(final byte digit) {
		final byte r = digits16[digit];
		if (r < 0)
			throw new ArrayIndexOutOfBoundsException();
		return r;
	}

	public static final int parseTimeZoneOffset(byte[] b
		return parseTimeZoneOffset(b
	}

	public static final int parseTimeZoneOffset(final byte[] b
			MutableInteger ptrResult) {
		final int v = parseBase10(b
		final int tzMins = v % 100;
		final int tzHours = v / 100;
		return tzHours * 60 + tzMins;
	}

	public static final int next(byte[] b
		final int sz = b.length;
		while (ptr < sz) {
			if (b[ptr++] == chrA)
				return ptr;
		}
		return ptr;
	}

	public static final int nextLF(byte[] b
		return next(b
	}

	public static final int nextLF(byte[] b
		final int sz = b.length;
		while (ptr < sz) {
			final byte c = b[ptr++];
			if (c == chrA || c == '\n')
				return ptr;
		}
		return ptr;
	}

	public static final int headerEnd(final byte[] b
		final int sz = b.length;
		while (ptr < sz) {
			final byte c = b[ptr++];
			if (c == '\n' && (ptr == sz || b[ptr] != ' ')) {
				return ptr - 1;
			}
		}
		return ptr - 1;
	}

	public static final int headerStart(byte[] headerName
		if (ptr != 0) {
			ptr = nextLF(b
		}
		while (ptr < b.length - (headerName.length + 1)) {
			boolean found = true;
			for (int i = 0; i < headerName.length; i++) {
				if (headerName[i] != b[ptr++]) {
					found = false;
					break;
				}
			}
			if (found && b[ptr++] == ' ') {
				return ptr;
			}
			ptr = nextLF(b
		}
		return -1;
	}

	public static final int prev(byte[] b
		if (ptr == b.length)
			--ptr;
		while (ptr >= 0) {
			if (b[ptr--] == chrA)
				return ptr;
		}
		return ptr;
	}

	public static final int prevLF(byte[] b
		return prev(b
	}

	public static final int prevLF(byte[] b
		if (ptr == b.length)
			--ptr;
		while (ptr >= 0) {
			final byte c = b[ptr--];
			if (c == chrA || c == '\n')
				return ptr;
		}
		return ptr;
	}

	public static final IntList lineMap(byte[] buf
		IntList map = new IntList((end - ptr) / 36);
		map.fillTo(1
		for (; ptr < end; ptr = nextLF(buf
			map.add(ptr);
		}
		map.add(end);
		return map;
	}

	public static final IntList lineMapOrBinary(byte[] buf
			throws BinaryBlobException {
		IntList map = lineMapOrNull(buf
		if (map == null) {
			throw new BinaryBlobException();
		}
		return map;
	}

	@Nullable
	private static IntList lineMapOrNull(byte[] buf
		IntList map = new IntList((end - ptr) / 36);
		map.add(Integer.MIN_VALUE);
		boolean foundLF = true;
		for (; ptr < end; ptr++) {
			if (foundLF) {
				map.add(ptr);
			}

			if (buf[ptr] == '\0') {
				return null;
			}

			foundLF = (buf[ptr] == '\n');
		}
		map.add(end);
		return map;
	}

	public static final int author(byte[] b
		final int sz = b.length;
		if (ptr == 0)
		while (ptr < sz && b[ptr] == 'p')
		return match(b
	}

	public static final int committer(byte[] b
		final int sz = b.length;
		if (ptr == 0)
		while (ptr < sz && b[ptr] == 'p')
		if (ptr < sz && b[ptr] == 'a')
			ptr = nextLF(b
		return match(b
	}

	public static final int tagger(byte[] b
		final int sz = b.length;
		if (ptr == 0)
		while (ptr < sz) {
			if (b[ptr] == '\n')
				return -1;
			final int m = match(b
			if (m >= 0)
				return m;
			ptr = nextLF(b
		}
		return -1;
	}

	public static final int encoding(byte[] b
		final int sz = b.length;
		while (ptr < sz) {
			if (b[ptr] == '\n')
				return -1;
			if (b[ptr] == 'e')
				break;
			ptr = nextLF(b
		}
		return match(b
	}

	@Nullable
	public static String parseEncodingName(byte[] b) {
		int enc = encoding(b
		if (enc < 0) {
			return null;
		}
		int lf = nextLF(b
		return decode(UTF_8
	}

	public static Charset parseEncoding(byte[] b) {
		String enc = parseEncodingName(b);
		if (enc == null) {
			return UTF_8;
		}

		String name = enc.trim();
		try {
			return Charset.forName(name);
		} catch (IllegalCharsetNameException
				| UnsupportedCharsetException badName) {
			Charset aliased = charsetForAlias(name);
			if (aliased != null) {
				return aliased;
			}
			throw badName;
		}
	}

	public static PersonIdent parsePersonIdent(String in) {
		return parsePersonIdent(Constants.encode(in)
	}

	public static PersonIdent parsePersonIdent(byte[] raw
		Charset cs;
		try {
			cs = parseEncoding(raw);
		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			cs = UTF_8;
		}

		final int emailB = nextLF(raw
		final int emailE = nextLF(raw
		if (emailB >= raw.length || raw[emailB] == '\n' ||
				(emailE >= raw.length - 1 && raw[emailE - 1] != '>'))
			return null;

		final int nameEnd = emailB - 2 >= nameB && raw[emailB - 2] == ' ' ?
				emailB - 2 : emailB - 1;
		final String name = decode(cs
		final String email = decode(cs

		final int tzBegin = lastIndexOfTrim(raw
				nextLF(raw
			return new PersonIdent(name

		final int whenBegin = Math.max(emailE
				lastIndexOfTrim(raw
			return new PersonIdent(name

		final long when = parseLongBase10(raw
		final int tz = parseTimeZoneOffset(raw
		return new PersonIdent(name
	}

	public static PersonIdent parsePersonIdentOnly(final byte[] raw
			final int nameB) {
		int stop = nextLF(raw
		int emailB = nextLF(raw
		int emailE = nextLF(raw
		final String name;
		final String email;
		if (emailE < stop) {
			email = decode(raw
		} else {
		}
		if (emailB < stop)
			name = decode(raw
		else
			name = decode(raw

		final MutableInteger ptrout = new MutableInteger();
		long when;
		int tz;
		if (emailE < stop) {
			when = parseLongBase10(raw
			tz = parseTimeZoneOffset(raw
		} else {
			when = 0;
			tz = 0;
		}
		return new PersonIdent(name
	}

	public static int endOfFooterLineKey(byte[] raw
		try {
			for (;;) {
				final byte c = raw[ptr];
				if (footerLineKeyChars[c] == 0) {
					if (c == ':')
						return ptr;
					return -1;
				}
				ptr++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}
	}

	public static String decode(byte[] buffer) {
		return decode(buffer
	}

	public static String decode(final byte[] buffer
			final int end) {
		return decode(UTF_8
	}

	public static String decode(Charset cs
		return decode(cs
	}

	public static String decode(final Charset cs
			final int start
		try {
			return decodeNoFallback(cs
		} catch (CharacterCodingException e) {
			return extractBinaryString(buffer
		}
	}

	public static String decodeNoFallback(final Charset cs
			final byte[] buffer
			throws CharacterCodingException {
		ByteBuffer b = ByteBuffer.wrap(buffer
		b.mark();

		try {
			return decode(b
		} catch (CharacterCodingException e) {
			b.reset();
		}

		if (!cs.equals(UTF_8)) {
			try {
				return decode(b
			} catch (CharacterCodingException e) {
				b.reset();
			}
		}

		Charset defcs = Charset.defaultCharset();
		if (!defcs.equals(cs) && !defcs.equals(UTF_8)) {
			try {
				return decode(b
			} catch (CharacterCodingException e) {
				b.reset();
			}
		}

		throw new CharacterCodingException();
	}

	public static String extractBinaryString(final byte[] buffer
			final int start
		final StringBuilder r = new StringBuilder(end - start);
		for (int i = start; i < end; i++)
			r.append((char) (buffer[i] & 0xff));
		return r.toString();
	}

	private static String decode(ByteBuffer b
			throws CharacterCodingException {
		final CharsetDecoder d = charset.newDecoder();
		d.onMalformedInput(CodingErrorAction.REPORT);
		d.onUnmappableCharacter(CodingErrorAction.REPORT);
		return d.decode(b).toString();
	}

	public static final int commitMessage(byte[] b
		final int sz = b.length;
		if (ptr == 0)
		while (ptr < sz && b[ptr] == 'p')

		return tagMessage(b
	}

	public static final int tagMessage(byte[] b
		final int sz = b.length;
		if (ptr == 0)
		while (ptr < sz && b[ptr] != '\n')
			ptr = nextLF(b
		if (ptr < sz && b[ptr] == '\n')
			return ptr + 1;
		return -1;
	}

	public static final int endOfParagraph(byte[] b
		int ptr = start;
		final int sz = b.length;
		while (ptr < sz && (b[ptr] != '\n' && b[ptr] != '\r'))
			ptr = nextLF(b
		if (ptr > start && b[ptr - 1] == '\n')
			ptr--;
		if (ptr > start && b[ptr - 1] == '\r')
			ptr--;
		return ptr;
	}

	public static int lastIndexOfTrim(byte[] raw
		while (pos >= 0 && raw[pos] == ' ')
			pos--;

		while (pos >= 0 && raw[pos] != ch)
			pos--;

		return pos;
	}

	private static Charset charsetForAlias(String name) {
		return encodingAliases.get(StringUtils.toLowerCase(name));
	}

	private RawParseUtils() {
	}
}
