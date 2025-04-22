
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.InvalidPatternException;
import org.eclipse.jgit.fnmatch.FileNameMatcher;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;

public class OpenSshConfigFile {


	private final File home;

	private final File configFile;

	private final String localUserName;

	private long lastModified;

	private static class State {
		Map<String

		Map<String

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			return "State [entries=" + entries + "
		}
	}

	private State state;

	public OpenSshConfigFile(@NonNull File home
			@NonNull String localUserName) {
		this.home = home;
		this.configFile = config;
		this.localUserName = localUserName;
		state = new State();
	}

	@NonNull
	public HostEntry lookup(@NonNull String hostName
			String userName) {
		final State cache = refresh();
		String cacheKey = toCacheKey(hostName
		HostEntry h = cache.hosts.get(cacheKey);
		if (h != null) {
			return h;
		}
		HostEntry fullConfig = new HostEntry();
		fullConfig.merge(cache.entries.get(DEFAULT_NAME));
		for (Map.Entry<String
			String pattern = e.getKey();
			if (isHostMatch(pattern
				fullConfig.merge(e.getValue());
			}
		}
		fullConfig.substitute(hostName
		cache.hosts.put(cacheKey
		return fullConfig;
	}

	@NonNull
	private String toCacheKey(@NonNull String hostName
			String userName) {
		String key = hostName;
		if (port > 0) {
			key = key + ':' + Integer.toString(port);
		}
		if (userName != null && !userName.isEmpty()) {
			key = userName + '@' + key;
		}
		return key;
	}

	private synchronized State refresh() {
		final long mtime = configFile.lastModified();
		if (mtime != lastModified) {
			State newState = new State();
			try (BufferedReader br = Files
					.newBufferedReader(configFile.toPath()
				newState.entries = parse(br);
			} catch (IOException | RuntimeException none) {
			}
			lastModified = mtime;
			state = newState;
		}
		return state;
	}

	private Map<String
			throws IOException {
		final Map<String
		final List<HostEntry> current = new ArrayList<>(4);
		String line;

		HostEntry defaults = new HostEntry();
		current.add(defaults);
		entries.put(DEFAULT_NAME

		while ((line = reader.readLine()) != null) {
			line = line.trim();
				continue;
			}
			String[] parts = line.split("[ \t]*[= \t]"
			String keyword = dequote(parts[0].trim());

			if (StringUtils.equalsIgnoreCase(SshConstants.HOST
				current.clear();
				for (String name : parseList(argValue)) {
					if (name == null || name.isEmpty()) {
						continue;
					}
					HostEntry c = entries.get(name);
					if (c == null) {
						c = new HostEntry();
						entries.put(name
					}
					current.add(c);
				}
				continue;
			}

			if (current.isEmpty()) {
				continue;
			}

			if (HostEntry.isListKey(keyword)) {
				List<String> args = validate(keyword
				for (HostEntry entry : current) {
					entry.setValue(keyword
				}
			} else if (!argValue.isEmpty()) {
				argValue = validate(keyword
				for (HostEntry entry : current) {
					entry.setValue(keyword
				}
			}
		}

		return entries;
	}

	private List<String> parseList(String argument) {
		List<String> result = new ArrayList<>(4);
		int start = 0;
		int length = argument.length();
		while (start < length) {
			if (Character.isSpaceChar(argument.charAt(start))) {
				start++;
				continue;
			}
			if (argument.charAt(start) == '"') {
				int stop = argument.indexOf('"'
				if (stop < start) {
					break;
				}
				result.add(argument.substring(start
				start = stop + 1;
			} else {
				int stop = start + 1;
				while (stop < length
						&& !Character.isSpaceChar(argument.charAt(stop))) {
					stop++;
				}
				result.add(argument.substring(start
				start = stop + 1;
			}
		}
		return result;
	}

	protected String validate(String key
		if (String.CASE_INSENSITIVE_ORDER.compare(key
				SshConstants.PREFERRED_AUTHENTICATIONS) == 0) {
			return stripWhitespace(value);
		}
		return value;
	}

	protected List<String> validate(String key
		return value;
	}

	private static boolean isHostMatch(String pattern
			return !patternMatchesHost(pattern.substring(1)
		} else {
			return patternMatchesHost(pattern
		}
	}

	private static boolean patternMatchesHost(String pattern
		if (pattern.indexOf('*') >= 0 || pattern.indexOf('?') >= 0) {
			final FileNameMatcher fn;
			try {
				fn = new FileNameMatcher(pattern
			} catch (InvalidPatternException e) {
				return false;
			}
			fn.append(name);
			return fn.isMatch();
		} else {
			return pattern.equals(name);
		}
	}

	private static String dequote(String value) {
				&& value.length() > 1)
			return value.substring(1
		return value;
	}

	private static String stripWhitespace(String value) {
		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isSpaceChar(value.charAt(i)))
				b.append(value.charAt(i));
		}
		return b.toString();
	}

	private static File toFile(String path
			return new File(home
		}
		File ret = new File(path);
		if (ret.isAbsolute()) {
			return ret;
		}
		return new File(home
	}

	public static int positive(String value) {
		if (value != null) {
			try {
				return Integer.parseUnsignedInt(value);
			} catch (NumberFormatException e) {
			}
		}
		return -1;
	}

	public static boolean flag(String value) {
		if (value == null) {
			return false;
		}
		return SshConstants.YES.equals(value) || SshConstants.ON.equals(value)
				|| SshConstants.TRUE.equals(value);
	}

	public String getLocalUserName() {
		return localUserName;
	}

	public static class HostEntry {

		private static final Set<String> MULTI_KEYS = new TreeSet<>(
				String.CASE_INSENSITIVE_ORDER);

		static {
			MULTI_KEYS.add(SshConstants.CERTIFICATE_FILE);
			MULTI_KEYS.add(SshConstants.IDENTITY_FILE);
			MULTI_KEYS.add(SshConstants.LOCAL_FORWARD);
			MULTI_KEYS.add(SshConstants.REMOTE_FORWARD);
			MULTI_KEYS.add(SshConstants.SEND_ENV);
		}

		private static final Set<String> LIST_KEYS = new TreeSet<>(
				String.CASE_INSENSITIVE_ORDER);

		static {
			LIST_KEYS.add(SshConstants.CANONICAL_DOMAINS);
			LIST_KEYS.add(SshConstants.GLOBAL_KNOWN_HOSTS_FILE);
			LIST_KEYS.add(SshConstants.SEND_ENV);
			LIST_KEYS.add(SshConstants.USER_KNOWN_HOSTS_FILE);
		}

		private Map<String

		private Map<String

		private Map<String

		public String getValue(String key) {
			String result = options != null ? options.get(key) : null;
			if (result == null) {
				List<String> values = listOptions != null ? listOptions.get(key)
						: null;
				if (values == null) {
					values = multiOptions != null ? multiOptions.get(key)
							: null;
				}
				if (values != null && !values.isEmpty()) {
					result = values.get(0);
				}
			}
			return result;
		}

		public List<String> getValues(String key) {
			List<String> values = listOptions != null ? listOptions.get(key)
					: null;
			if (values == null) {
				values = multiOptions != null ? multiOptions.get(key) : null;
			}
			if (values == null || values.isEmpty()) {
				return new ArrayList<>();
			}
			return new ArrayList<>(values);
		}

		public void setValue(String key
			if (value == null) {
				if (multiOptions != null) {
					multiOptions.remove(key);
				}
				if (listOptions != null) {
					listOptions.remove(key);
				}
				if (options != null) {
					options.remove(key);
				}
				return;
			}
			if (MULTI_KEYS.contains(key)) {
				if (multiOptions == null) {
					multiOptions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				List<String> values = multiOptions.get(key);
				if (values == null) {
					values = new ArrayList<>(4);
					multiOptions.put(key
				}
				values.add(value);
			} else {
				if (options == null) {
					options = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				if (!options.containsKey(key)) {
					options.put(key
				}
			}
		}

		public void setValue(String key
			if (values.isEmpty()) {
				return;
			}
			if (MULTI_KEYS.contains(key)) {
				if (multiOptions == null) {
					multiOptions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				List<String> items = multiOptions.get(key);
				if (items == null) {
					items = new ArrayList<>(values);
					multiOptions.put(key
				} else {
					items.addAll(values);
				}
			} else {
				if (listOptions == null) {
					listOptions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				if (!listOptions.containsKey(key)) {
					listOptions.put(key
				}
			}
		}

		public static boolean isListKey(String key) {
			return LIST_KEYS.contains(key.toUpperCase(Locale.ROOT));
		}

		void merge(HostEntry entry) {
			if (entry == null) {
				return;
			}
			if (entry.options != null) {
				if (options == null) {
					options = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				for (Map.Entry<String
						.entrySet()) {
					if (!options.containsKey(item.getKey())) {
						options.put(item.getKey()
					}
				}
			}
			if (entry.listOptions != null) {
				if (listOptions == null) {
					listOptions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				for (Map.Entry<String
						.entrySet()) {
					if (!listOptions.containsKey(item.getKey())) {
						listOptions.put(item.getKey()
					}
				}

			}
			if (entry.multiOptions != null) {
				if (multiOptions == null) {
					multiOptions = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
				}
				for (Map.Entry<String
						.entrySet()) {
					List<String> values = multiOptions.get(item.getKey());
					if (values == null) {
						values = new ArrayList<>(item.getValue());
						multiOptions.put(item.getKey()
					} else {
						values.addAll(item.getValue());
					}
				}
			}
		}

		private List<String> substitute(List<String> values
				Replacer r) {
			List<String> result = new ArrayList<>(values.size());
			for (String value : values) {
				result.add(r.substitute(value
			}
			return result;
		}

		private List<String> replaceTilde(List<String> values
			List<String> result = new ArrayList<>(values.size());
			for (String value : values) {
				result.add(toFile(value
			}
			return result;
		}

		void substitute(String originalHostName
				String localUserName
			int p = port >= 0 ? port : positive(getValue(SshConstants.PORT));
			if (p < 0) {
				p = SshConstants.SSH_DEFAULT_PORT;
			}
			String u = userName != null && !userName.isEmpty() ? userName
					: getValue(SshConstants.USER);
			if (u == null || u.isEmpty()) {
				u = localUserName;
			}
			Replacer r = new Replacer(originalHostName
					home);
			if (options != null) {
				String hostName = options.get(SshConstants.HOST_NAME);
				if (hostName == null || hostName.isEmpty()) {
					options.put(SshConstants.HOST_NAME
				} else {
					hostName = r.substitute(hostName
					options.put(SshConstants.HOST_NAME
					r.update('h'
				}
			}
			if (multiOptions != null) {
				List<String> values = multiOptions
						.get(SshConstants.IDENTITY_FILE);
				if (values != null) {
					values = substitute(values
					values = replaceTilde(values
					multiOptions.put(SshConstants.IDENTITY_FILE
				}
				values = multiOptions.get(SshConstants.CERTIFICATE_FILE);
				if (values != null) {
					values = substitute(values
					values = replaceTilde(values
					multiOptions.put(SshConstants.CERTIFICATE_FILE
				}
			}
			if (listOptions != null) {
				List<String> values = listOptions
						.get(SshConstants.USER_KNOWN_HOSTS_FILE);
				if (values != null) {
					values = replaceTilde(values
					listOptions.put(SshConstants.USER_KNOWN_HOSTS_FILE
				}
			}
			if (options != null) {
				String value = options.get(SshConstants.IDENTITY_AGENT);
				if (value != null) {
					value = r.substitute(value
					value = toFile(value
					options.put(SshConstants.IDENTITY_AGENT
				}
				value = options.get(SshConstants.CONTROL_PATH);
				if (value != null) {
					value = r.substitute(value
					value = toFile(value
					options.put(SshConstants.CONTROL_PATH
				}
				value = options.get(SshConstants.LOCAL_COMMAND);
				if (value != null) {
					value = r.substitute(value
					options.put(SshConstants.LOCAL_COMMAND
				}
				value = options.get(SshConstants.REMOTE_COMMAND);
				if (value != null) {
					value = r.substitute(value
					options.put(SshConstants.REMOTE_COMMAND
				}
				value = options.get(SshConstants.PROXY_COMMAND);
				if (value != null) {
					value = r.substitute(value
					options.put(SshConstants.PROXY_COMMAND
				}
			}
		}

		@NonNull
		public Map<String
			if (options == null) {
				return Collections.emptyMap();
			}
			return Collections.unmodifiableMap(options);
		}

		@NonNull
		public Map<String
			if (listOptions == null && multiOptions == null) {
				return Collections.emptyMap();
			}
			Map<String
					String.CASE_INSENSITIVE_ORDER);
			if (multiOptions != null) {
				allValues.putAll(multiOptions);
			}
			if (listOptions != null) {
				allValues.putAll(listOptions);
			}
			return Collections.unmodifiableMap(allValues);
		}

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			return "HostEntry [options=" + options + "
					+ multiOptions + "
		}
	}

	private static class Replacer {
		private final Map<Character

		public Replacer(String host
				String localUserName
			replacements.put(Character.valueOf('%')
			replacements.put(Character.valueOf('d')
			replacements.put(Character.valueOf('h')
			String localhost = SystemReader.getInstance().getHostname();
			replacements.put(Character.valueOf('l')
			int period = localhost.indexOf('.');
			if (period > 0) {
				localhost = localhost.substring(0
			}
			replacements.put(Character.valueOf('L')
			replacements.put(Character.valueOf('n')
			replacements.put(Character.valueOf('p')
			replacements.put(Character.valueOf('r')
			replacements.put(Character.valueOf('u')
			replacements.put(Character.valueOf('C')
					substitute("%l%h%p%r"
			replacements.put(Character.valueOf('T')
		}

		public void update(char key
			replacements.put(Character.valueOf(key)
				replacements.put(Character.valueOf('C')
						substitute("%l%h%p%r"
			}
		}

		public String substitute(String input
			if (input == null || input.length() <= 1
					|| input.indexOf('%') < 0) {
				return input;
			}
			StringBuilder builder = new StringBuilder();
			int start = 0;
			int length = input.length();
			while (start < length) {
				int percent = input.indexOf('%'
				if (percent < 0 || percent + 1 >= length) {
					builder.append(input.substring(start));
					break;
				}
				String replacement = null;
				char ch = input.charAt(percent + 1);
				if (ch == '%' || allowed.indexOf(ch) >= 0) {
					replacement = replacements.get(Character.valueOf(ch));
				}
				if (replacement == null) {
					builder.append(input.substring(start
				} else {
					builder.append(input.substring(start
							.append(replacement);
				}
				start = percent + 2;
			}
			return builder.toString();
		}
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "OpenSshConfig [home=" + home + "
				+ "
	}
}
