
package org.eclipse.jgit.attributes;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;
import org.eclipse.jgit.util.RawParseUtils;

public class Attributes {

	private final List<Object> items = new ArrayList<Object>();

	public final List<String> getPatterns() {
		final List<String> patterns = new ArrayList<String>();
		for (Object item : items) {
			if (!(item instanceof Entry))
				continue;

			final Entry entry = (Entry) item;
			patterns.add(entry.pattern);
		}

		return patterns;
	}

	public boolean isEmpty() {
		return items.size() == 0;
	}

	public boolean collect(String relativePath
			Set<String> keysToIgnore) {
		relativePath = RawParseUtils.pathTrimLeadingSlash(relativePath);

		if (keysToIgnore == null)
			keysToIgnore = new HashSet<String>();

		for (int index = items.size() - 1; index >= 0; index--) {
			final Entry entry = getAsMatchingEntry(relativePath
					items.get(index));
			if (entry == null)
				continue;

			for (Attribute attribute : entry.attributes) {
				final String key = attribute.getKey();
				if (!keysToIgnore.add(key))
					continue;

				if (!collector.collect(attribute))
					return false;
			}
		}

		return true;
	}

	public void parse(Reader reader) throws IOException {
		clear();

		StringBuilder builder = new StringBuilder();
		for (;;) {
			final int ch = reader.read();
			if (ch == -1) {
				parseLine(builder.toString());
				break;
			}

			builder.append((char) ch);
			if (ch == '\n') {
				parseLine(builder.toString());
				builder = new StringBuilder();
			}
		}
	}

	public String toText() {
		final StringBuilder builder = new StringBuilder();
		for (Object item : items) {
			if (item instanceof Entry)
				builder.append(((Entry) item).line);
			else
				builder.append(item.toString());
		}

		return builder.toString();
	}

	public void clear() {
		items.clear();
	}

	private void parseLine(String line) throws IOException {
		final Entry entry = parseEntry(line);
		if (entry != null)
			items.add(entry);
		else
			items.add(line);
	}

	private static Entry parseEntry(String line) throws IOException {
		String pattern = null;
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final String[] tokens = line.replace('\t'
		for (int index = 0; index < tokens.length; index++) {
			String token = tokens[index].trim();
			if (index == tokens.length - 1)
				token = token.trim();

			if (token.length() == 0)
				continue;

			if (pattern == null) {
				if (token.startsWith("#") || token.startsWith("<<<<<<<")
						|| token.startsWith("=======")
						|| token.startsWith(">>>>>>>"))
					return null;

				pattern = token;
				continue;
			}

			final Attribute attribute = parseAttribute(token);
			if (attribute == null)
				return null;

			attributes.add(attribute);
		}

		if (pattern == null)
			return null;

		try {
			return new Entry(pattern
					attributes.toArray(new Attribute[attributes.size()])
		} catch (InvalidPatternException ex) {
			throw new IOException(ex.getMessage());
		}
	}

	private static Attribute parseAttribute(String token) {
		if (token.startsWith("!"))
			return createValidAttribute(token.substring(1)

		if (token.startsWith("-"))
			return createValidAttribute(token.substring(1)
					AttributeValue.UNSET);

		final int equalsIndex = token.indexOf("=");
		if (equalsIndex == -1)
			return createValidAttribute(token

		return createValidAttribute(token.substring(0
				AttributeValue.createValue(token.substring(equalsIndex + 1)));
	}

	private static Attribute createValidAttribute(String name
			AttributeValue value) {
		if (!isValidAttributeName(name))
			return null;

		return new Attribute(name
	}

	private static boolean isValidAttributeName(String name) {
		if (name.length() == 0)
			return false;

		if (name.charAt(0) == '-')
			return false;

		for (int index = 0; index < name.length(); index++) {
			final char ch = name.charAt(index);
			if (!(ch == '-' || ch == '.' || ch == '_'
					|| ('0' <= ch && ch <= '9') || ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z')))
				return false;
		}

		return true;
	}

	private static Entry getAsMatchingEntry(String path
		if (!(item instanceof Entry))
			return null;

		final Entry entry = (Entry) item;
		final FileNameMatcher matcher = entry.fileNameMatcher;
		matcher.reset();

		final String match = entry.pattern.contains("/") ? path : RawParseUtils
				.pathTail(path);
		matcher.append(match);
		return matcher.isMatch() ? entry : null;
	}

	private static class Entry {
		private final String pattern;

		private final FileNameMatcher fileNameMatcher;

		private final Attribute[] attributes;

		private final String line;

		public Entry(String pattern
				throws InvalidPatternException {
			this.pattern = pattern;
			this.fileNameMatcher = new FileNameMatcher(pattern
					'/'));
			this.attributes = attributes;
			this.line = line;
		}

		@Override
		public String toString() {
			return pattern + "=" + Arrays.toString(attributes);
		}
	}
}
