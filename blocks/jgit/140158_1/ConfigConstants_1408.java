
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.ListenerList;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.util.RawParseUtils;

public class Config {

	private static final String[] EMPTY_STRING_ARRAY = {};

	static final long KiB = 1024;
	static final long MiB = 1024 * KiB;
	static final long GiB = 1024 * MiB;
	private static final int MAX_DEPTH = 10;

	private static final TypedConfigGetter DEFAULT_GETTER = new DefaultTypedConfigGetter();

	private static TypedConfigGetter typedGetter = DEFAULT_GETTER;

	private final ListenerList listeners = new ListenerList();

	private final AtomicReference<ConfigSnapshot> state;

	private final Config baseConfig;

	static final String MAGIC_EMPTY_VALUE = new String();

	public Config() {
		this(null);
	}

	public Config(Config defaultConfig) {
		baseConfig = defaultConfig;
		state = new AtomicReference<>(newState());
	}

	public static void setTypedConfigGetter(TypedConfigGetter getter) {
		typedGetter = getter == null ? DEFAULT_GETTER : getter;
	}

	static String escapeValue(String x) {
		if (x.isEmpty()) {
		}

		boolean needQuote = x.charAt(0) == ' ' || x.charAt(x.length() - 1) == ' ';
		StringBuilder r = new StringBuilder(x.length());
		for (int k = 0; k < x.length(); k++) {
			char c = x.charAt(k);
			switch (c) {
			case '\0':
				throw new IllegalArgumentException(
						JGitText.get().configValueContainsNullByte);

			case '\n':
				r.append('\\').append('n');
				break;

			case '\t':
				r.append('\\').append('t');
				break;

			case '\b':
				r.append('\\').append('b');
				break;

			case '\\':
				r.append('\\').append('\\');
				break;

			case '"':
				r.append('\\').append('"');
				break;

			case '#':
			case ';':
				needQuote = true;
				r.append(c);
				break;

			default:
				r.append(c);
				break;
			}
		}

		return needQuote ? '"' + r.toString() + '"' : r.toString();
	}

	static String escapeSubsection(String x) {
		if (x.isEmpty()) {
		}

		StringBuilder r = new StringBuilder(x.length() + 2).append('"');
		for (int k = 0; k < x.length(); k++) {
			char c = x.charAt(k);

			switch (c) {
			case '\0':
				throw new IllegalArgumentException(
						JGitText.get().configSubsectionContainsNullByte);

			case '\n':
				throw new IllegalArgumentException(
						JGitText.get().configSubsectionContainsNewline);

			case '\\':
			case '"':
				r.append('\\').append(c);
				break;

			default:
				r.append(c);
				break;
			}
		}

		return r.append('"').toString();
	}

	public int getInt(final String section
			final int defaultValue) {
		return typedGetter.getInt(this
	}

	public int getInt(final String section
			final String name
		return typedGetter.getInt(this
				defaultValue);
	}

	public long getLong(String section
		return typedGetter.getLong(this
	}

	public long getLong(final String section
			final String name
		return typedGetter.getLong(this
				defaultValue);
	}

	public boolean getBoolean(final String section
			final boolean defaultValue) {
		return typedGetter.getBoolean(this
	}

	public boolean getBoolean(final String section
			final String name
		return typedGetter.getBoolean(this
				defaultValue);
	}

	public <T extends Enum<?>> T getEnum(final String section
			final String subsection
		final T[] all = allValuesOf(defaultValue);
		return typedGetter.getEnum(this
				defaultValue);
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] allValuesOf(T value) {
		try {
		} catch (Exception err) {
			String typeName = value.getClass().getName();
			String msg = MessageFormat.format(
					JGitText.get().enumValuesNotAvailable
			throw new IllegalArgumentException(msg
		}
	}

	public <T extends Enum<?>> T getEnum(final T[] all
			final String subsection
		return typedGetter.getEnum(this
				defaultValue);
	}

	public String getString(final String section
			final String name) {
		return getRawString(section
	}

	public String[] getStringList(final String section
			final String name) {
		String[] base;
		if (baseConfig != null)
			base = baseConfig.getStringList(section
		else
			base = EMPTY_STRING_ARRAY;

		String[] self = getRawStringList(section
		if (self == null)
			return base;
		if (base.length == 0)
			return self;
		String[] res = new String[base.length + self.length];
		int n = base.length;
		System.arraycopy(base
		System.arraycopy(self
		return res;
	}

	public long getTimeUnit(String section
			long defaultValue
		return typedGetter.getTimeUnit(this
				defaultValue
	}

	public List<RefSpec> getRefSpecs(String section
			String name) {
		return typedGetter.getRefSpecs(this
	}

	public Set<String> getSubsections(String section) {
		return getState().getSubsections(section);
	}

	public Set<String> getSections() {
		return getState().getSections();
	}

	public Set<String> getNames(String section) {
		return getNames(section
	}

	public Set<String> getNames(String section
		return getState().getNames(section
	}

	public Set<String> getNames(String section
		return getState().getNames(section
	}

	public Set<String> getNames(String section
			boolean recursive) {
		return getState().getNames(section
	}

	@SuppressWarnings("unchecked")
	public <T> T get(SectionParser<T> parser) {
		final ConfigSnapshot myState = getState();
		T obj = (T) myState.cache.get(parser);
		if (obj == null) {
			obj = parser.parse(this);
			myState.cache.put(parser
		}
		return obj;
	}

	public void uncache(SectionParser<?> parser) {
		state.get().cache.remove(parser);
	}

	public ListenerHandle addChangeListener(ConfigChangedListener listener) {
		return listeners.addConfigChangedListener(listener);
	}

	protected boolean notifyUponTransientChanges() {
		return true;
	}

	protected void fireConfigChangedEvent() {
		listeners.dispatch(new ConfigChangedEvent());
	}

	String getRawString(final String section
			final String name) {
		String[] lst = getRawStringList(section
		if (lst != null) {
			return lst[lst.length - 1];
		} else if (baseConfig != null) {
			return baseConfig.getRawString(section
		} else {
			return null;
		}
	}

	private String[] getRawStringList(String section
			String name) {
		return state.get().get(section
	}

	private ConfigSnapshot getState() {
		ConfigSnapshot cur
		do {
			cur = state.get();
			final ConfigSnapshot base = getBaseState();
			if (cur.baseState == base)
				return cur;
			upd = new ConfigSnapshot(cur.entryList
		} while (!state.compareAndSet(cur
		return upd;
	}

	private ConfigSnapshot getBaseState() {
		return baseConfig != null ? baseConfig.getState() : null;
	}

	public void setInt(final String section
			final String name
		setLong(section
	}

	public void setLong(final String section
			final String name
		final String s;

		if (value >= GiB && (value % GiB) == 0)
		else if (value >= MiB && (value % MiB) == 0)
		else if (value >= KiB && (value % KiB) == 0)
		else
			s = String.valueOf(value);

		setString(section
	}

	public void setBoolean(final String section
			final String name
		setString(section
	}

	public <T extends Enum<?>> void setEnum(final String section
			final String subsection
		String n;
		if (value instanceof ConfigEnum)
			n = ((ConfigEnum) value).toConfigValue();
		else
			n = value.name().toLowerCase(Locale.ROOT).replace('_'
		setString(section
	}

	public void setString(final String section
			final String name
		setStringList(section
				.singletonList(value));
	}

	public void unset(final String section
			final String name) {
		setStringList(section
				.<String> emptyList());
	}

	public void unsetSection(String section
		ConfigSnapshot src
		do {
			src = state.get();
			res = unsetSection(src
		} while (!state.compareAndSet(src
	}

	private ConfigSnapshot unsetSection(final ConfigSnapshot srcState
			final String section
			final String subsection) {
		final int max = srcState.entryList.size();
		final ArrayList<ConfigLine> r = new ArrayList<>(max);

		boolean lastWasMatch = false;
		for (ConfigLine e : srcState.entryList) {
			if (e.includedFrom == null && e.match(section
				lastWasMatch = true;
				continue;
			}

			if (lastWasMatch && e.section == null && e.subsection == null)
			r.add(e);
		}

		return newState(r);
	}

	public void setStringList(final String section
			final String name
		ConfigSnapshot src
		do {
			src = state.get();
			res = replaceStringList(src
		} while (!state.compareAndSet(src
		if (notifyUponTransientChanges())
			fireConfigChangedEvent();
	}

	private ConfigSnapshot replaceStringList(final ConfigSnapshot srcState
			final String section
			final List<String> values) {
		final List<ConfigLine> entries = copy(srcState
		int entryIndex = 0;
		int valueIndex = 0;
		int insertPosition = -1;

		while (entryIndex < entries.size() && valueIndex < values.size()) {
			final ConfigLine e = entries.get(entryIndex);
			if (e.includedFrom == null && e.match(section
				entries.set(entryIndex
				insertPosition = entryIndex + 1;
			}
			entryIndex++;
		}

		if (valueIndex == values.size() && entryIndex < entries.size()) {
			while (entryIndex < entries.size()) {
				final ConfigLine e = entries.get(entryIndex++);
				if (e.includedFrom == null
						&& e.match(section
					entries.remove(--entryIndex);
			}
		}

		if (valueIndex < values.size() && entryIndex == entries.size()) {
			if (insertPosition < 0) {
				insertPosition = findSectionEnd(entries
						true);
			}
			if (insertPosition < 0) {
				final ConfigLine e = new ConfigLine();
				e.section = section;
				e.subsection = subsection;
				entries.add(e);
				insertPosition = entries.size();
			}
			while (valueIndex < values.size()) {
				final ConfigLine e = new ConfigLine();
				e.section = section;
				e.subsection = subsection;
				e.name = name;
				e.value = values.get(valueIndex++);
				entries.add(insertPosition++
			}
		}

		return newState(entries);
	}

	private static List<ConfigLine> copy(final ConfigSnapshot src
			final List<String> values) {
		final int max = src.entryList.size() + values.size() + 1;
		final ArrayList<ConfigLine> r = new ArrayList<>(max);
		r.addAll(src.entryList);
		return r;
	}

	private static int findSectionEnd(final List<ConfigLine> entries
			final String section
			boolean skipIncludedLines) {
		for (int i = 0; i < entries.size(); i++) {
			ConfigLine e = entries.get(i);
			if (e.includedFrom != null && skipIncludedLines) {
				continue;
			}

			if (e.match(section
				i++;
				while (i < entries.size()) {
					e = entries.get(i);
					if (e.match(section
						i++;
					else
						break;
				}
				return i;
			}
		}
		return -1;
	}

	public String toText() {
		final StringBuilder out = new StringBuilder();
		for (ConfigLine e : state.get().entryList) {
			if (e.includedFrom != null)
				continue;
			if (e.prefix != null)
				out.append(e.prefix);
			if (e.section != null && e.name == null) {
				out.append('[');
				out.append(e.section);
				if (e.subsection != null) {
					out.append(' ');
					String escaped = escapeValue(e.subsection);
					if (!quoted)
						out.append('"');
					out.append(escaped);
					if (!quoted)
						out.append('"');
				}
				out.append(']');
			} else if (e.section != null && e.name != null) {
					out.append('\t');
				out.append(e.name);
				if (MAGIC_EMPTY_VALUE != e.value) {
					if (e.value != null) {
						out.append(' ');
						out.append(escapeValue(e.value));
					}
				}
				if (e.suffix != null)
					out.append(' ');
			}
			if (e.suffix != null)
				out.append(e.suffix);
			out.append('\n');
		}
		return out.toString();
	}

	public void fromText(String text) throws ConfigInvalidException {
		state.set(newState(fromTextRecurse(text
	}

	private List<ConfigLine> fromTextRecurse(String text
			String includedFrom) throws ConfigInvalidException {
		if (depth > MAX_DEPTH) {
			throw new ConfigInvalidException(
					JGitText.get().tooManyIncludeRecursions);
		}
		final List<ConfigLine> newEntries = new ArrayList<>();
		final StringReader in = new StringReader(text);
		ConfigLine last = null;
		ConfigLine e = new ConfigLine();
		e.includedFrom = includedFrom;
		for (;;) {
			int input = in.read();
			if (-1 == input) {
				if (e.section != null)
					newEntries.add(e);
				break;
			}

			final char c = (char) input;
			if ('\n' == c) {
				newEntries.add(e);
				if (e.section != null)
					last = e;
				e = new ConfigLine();
				e.includedFrom = includedFrom;
			} else if (e.suffix != null) {
				e.suffix += c;

			} else if (';' == c || '#' == c) {
				e.suffix = String.valueOf(c);

			} else if (e.section == null && Character.isWhitespace(c)) {
				if (e.prefix == null)
				e.prefix += c;

			} else if ('[' == c) {
				e.section = readSectionName(in);
				input = in.read();
				if ('"' == input) {
					e.subsection = readSubsectionName(in);
					input = in.read();
				}
				if (']' != input)
					throw new ConfigInvalidException(JGitText.get().badGroupHeader);

			} else if (last != null) {
				e.section = last.section;
				e.subsection = last.subsection;
				in.reset();
				e.name = readKeyName(in);
					e.name = e.name.substring(0
					e.value = MAGIC_EMPTY_VALUE;
				} else
					e.value = readValue(in);

					addIncludedConfig(newEntries
				}
			} else
				throw new ConfigInvalidException(JGitText.get().invalidLineInConfigFile);
		}

		return newEntries;
	}

	protected byte[] readIncludedConfig(String relPath)
			throws ConfigInvalidException {
		return null;
	}

	private void addIncludedConfig(final List<ConfigLine> newEntries
			ConfigLine line
				line.value == null || line.value.equals(MAGIC_EMPTY_VALUE)) {
			throw new ConfigInvalidException(MessageFormat.format(
					JGitText.get().invalidLineInConfigFileWithParam
		}
		byte[] bytes = readIncludedConfig(line.value);
		if (bytes == null) {
			return;
		}

		String decoded;
		if (isUtf8(bytes)) {
			decoded = RawParseUtils.decode(UTF_8
		} else {
			decoded = RawParseUtils.decode(bytes);
		}
		try {
			newEntries.addAll(fromTextRecurse(decoded
		} catch (ConfigInvalidException e) {
			throw new ConfigInvalidException(MessageFormat
					.format(JGitText.get().cannotReadFile
		}
	}

	private ConfigSnapshot newState() {
		return new ConfigSnapshot(Collections.<ConfigLine> emptyList()
				getBaseState());
	}

	private ConfigSnapshot newState(List<ConfigLine> entries) {
		return new ConfigSnapshot(Collections.unmodifiableList(entries)
				getBaseState());
	}

	protected void clear() {
		state.set(newState());
	}

	protected boolean isUtf8(final byte[] bytes) {
		return bytes.length >= 3 && bytes[0] == (byte) 0xEF
				&& bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF;
	}

	private static String readSectionName(StringReader in)
			throws ConfigInvalidException {
		final StringBuilder name = new StringBuilder();
		for (;;) {
			int c = in.read();
			if (c < 0)
				throw new ConfigInvalidException(JGitText.get().unexpectedEndOfConfigFile);

			if (']' == c) {
				in.reset();
				break;
			}

			if (' ' == c || '\t' == c) {
				for (;;) {
					c = in.read();
					if (c < 0)
						throw new ConfigInvalidException(JGitText.get().unexpectedEndOfConfigFile);

					if ('"' == c) {
						in.reset();
						break;
					}

					if (' ' == c || '\t' == c)
					throw new ConfigInvalidException(MessageFormat.format(JGitText.get().badSectionEntry
				}
				break;
			}

			if (Character.isLetterOrDigit((char) c) || '.' == c || '-' == c)
				name.append((char) c);
			else
				throw new ConfigInvalidException(MessageFormat.format(JGitText.get().badSectionEntry
		}
		return name.toString();
	}

	private static String readKeyName(StringReader in)
			throws ConfigInvalidException {
		final StringBuilder name = new StringBuilder();
		for (;;) {
			int c = in.read();
			if (c < 0)
				throw new ConfigInvalidException(JGitText.get().unexpectedEndOfConfigFile);

			if ('=' == c)
				break;

			if (' ' == c || '\t' == c) {
				for (;;) {
					c = in.read();
					if (c < 0)
						throw new ConfigInvalidException(JGitText.get().unexpectedEndOfConfigFile);

					if ('=' == c)
						break;

					if (';' == c || '#' == c || '\n' == c) {
						in.reset();
						break;
					}

					if (' ' == c || '\t' == c)
					throw new ConfigInvalidException(JGitText.get().badEntryDelimiter);
				}
				break;
			}

			if (Character.isLetterOrDigit((char) c) || c == '-') {
				name.append((char) c);
			} else if ('\n' == c) {
				in.reset();
				name.append((char) c);
				break;
			} else
				throw new ConfigInvalidException(MessageFormat.format(JGitText.get().badEntryName
		}
		return name.toString();
	}

	private static String readSubsectionName(StringReader in)
			throws ConfigInvalidException {
		StringBuilder r = new StringBuilder();
		for (;;) {
			int c = in.read();
			if (c < 0) {
				break;
			}

			if ('\n' == c) {
				throw new ConfigInvalidException(
						JGitText.get().newlineInQuotesNotAllowed);
			}
			if ('\\' == c) {
				c = in.read();
				switch (c) {
				case -1:
					throw new ConfigInvalidException(JGitText.get().endOfFileInEscape);

				case '\\':
				case '"':
					r.append((char) c);
					continue;

				default:
					r.append((char) c);
					continue;
				}
			}
			if ('"' == c) {
				break;
			}

			r.append((char) c);
		}
		return r.toString();
	}

	private static String readValue(StringReader in)
			throws ConfigInvalidException {
		StringBuilder value = new StringBuilder();
		StringBuilder trailingSpaces = null;
		boolean quote = false;
		boolean inLeadingSpace = true;

		for (;;) {
			int c = in.read();
			if (c < 0) {
				break;
			}
			if ('\n' == c) {
				if (quote) {
					throw new ConfigInvalidException(
							JGitText.get().newlineInQuotesNotAllowed);
				}
				in.reset();
				break;
			}

			if (!quote && (';' == c || '#' == c)) {
				if (trailingSpaces != null) {
					trailingSpaces.setLength(0);
				}
				in.reset();
				break;
			}

			char cc = (char) c;
			if (Character.isWhitespace(cc)) {
				if (inLeadingSpace) {
					continue;
				}
				if (trailingSpaces == null) {
					trailingSpaces = new StringBuilder();
				}
				trailingSpaces.append(cc);
				continue;
			} else {
				inLeadingSpace = false;
				if (trailingSpaces != null) {
					value.append(trailingSpaces);
					trailingSpaces.setLength(0);
				}
			}

			if ('\\' == c) {
				c = in.read();
				switch (c) {
				case -1:
					throw new ConfigInvalidException(JGitText.get().endOfFileInEscape);
				case '\n':
					continue;
				case 't':
					value.append('\t');
					continue;
				case 'b':
					value.append('\b');
					continue;
				case 'n':
					value.append('\n');
					continue;
				case '\\':
					value.append('\\');
					continue;
				case '"':
					value.append('"');
					continue;
				default:
					throw new ConfigInvalidException(MessageFormat.format(
							JGitText.get().badEscape
							Character.valueOf(((char) c))));
				}
			}

			if ('"' == c) {
				quote = !quote;
				continue;
			}

			value.append(cc);
		}
		return value.length() > 0 ? value.toString() : null;
	}

	public static interface SectionParser<T> {
		T parse(Config cfg);
	}

	private static class StringReader {
		private final char[] buf;

		private int pos;

		StringReader(String in) {
			buf = in.toCharArray();
		}

		int read() {
			try {
				return buf[pos++];
			} catch (ArrayIndexOutOfBoundsException e) {
				pos = buf.length;
				return -1;
			}
		}

		void reset() {
			pos--;
		}
	}

	public static interface ConfigEnum {
		String toConfigValue();

		boolean matchConfigValue(String in);
	}
}
