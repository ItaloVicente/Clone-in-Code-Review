		final State cache = refresh();
		Host h = cache.hosts.get(hostName);
		if (h != null) {
			return h;
		}
		HostEntry fullConfig = new HostEntry();
		fullConfig.merge(cache.entries.get(HostEntry.DEFAULT_NAME));
		for (Map.Entry<String, HostEntry> e : cache.entries.entrySet()) {
			String key = e.getKey();
			if (isHostMatch(key, hostName)) {
				fullConfig.merge(e.getValue());
			}
		}
		fullConfig.substitute(hostName, home);
		h = new Host(fullConfig, hostName, home);
		cache.hosts.put(hostName, h);
		return h;
	}

	private synchronized State refresh() {
		final long mtime = configFile.lastModified();
		if (mtime != lastModified) {
			State newState = new State();
			try (FileInputStream in = new FileInputStream(configFile)) {
				newState.entries = parse(in);
			} catch (IOException none) {
			}
			lastModified = mtime;
			state = newState;
		}
		return state;
	}

	private Map<String, HostEntry> parse(InputStream in)
			throws IOException {
		final Map<String, HostEntry> m = new LinkedHashMap<>();
		final BufferedReader br = new BufferedReader(
				new InputStreamReader(in, UTF_8));
		final List<HostEntry> current = new ArrayList<>(4);
		String line;

		HostEntry defaults = new HostEntry();
		current.add(defaults);
		m.put(HostEntry.DEFAULT_NAME, defaults);

		while ((line = br.readLine()) != null) {
			line = line.trim();
				continue;
			}
			String[] parts = line.split("[ \t]*[= \t]", 2); //$NON-NLS-1$
			String keyword = dequote(parts[0].trim());

			if (StringUtils.equalsIgnoreCase("Host", keyword)) { //$NON-NLS-1$
				current.clear();
				for (String name : HostEntry.parseList(argValue)) {
					if (name == null || name.isEmpty()) {
						continue;
					}
					HostEntry c = m.get(name);
					if (c == null) {
						c = new HostEntry();
						m.put(name, c);
					}
					current.add(c);
				}
				continue;
			}

			if (current.isEmpty()) {
				continue;
			}

			if (HostEntry.isListKey(keyword)) {
				List<String> args = HostEntry.parseList(argValue);
				for (HostEntry entry : current) {
					entry.setValue(keyword, args);
				}
			} else if (!argValue.isEmpty()) {
				argValue = dequote(argValue);
				for (HostEntry entry : current) {
					entry.setValue(keyword, argValue);
				}
			}
		}

		return m;
	}

	private static boolean isHostMatch(final String pattern,
			final String name) {
			return !patternMatchesHost(pattern.substring(1), name);
		} else {
			return patternMatchesHost(pattern, name);
		}
	}

	private static boolean patternMatchesHost(final String pattern,
			final String name) {
		if (pattern.indexOf('*') >= 0 || pattern.indexOf('?') >= 0) {
			final FileNameMatcher fn;
			try {
				fn = new FileNameMatcher(pattern, null);
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
			return value.substring(1, value.length() - 1);
		return value;
	}

	private static String nows(String value) {
		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isSpaceChar(value.charAt(i)))
				b.append(value.charAt(i));
		}
		return b.toString();
	}

	private static Boolean yesno(String value) {
		if (StringUtils.equalsIgnoreCase("yes", value)) //$NON-NLS-1$
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	private static File toFile(String path, File home) {
			return new File(home, path.substring(2));
		}
		File ret = new File(path);
		if (ret.isAbsolute()) {
			return ret;
		}
		return new File(home, path);
	}

	private static int positive(String value) {
		if (value != null) {
			try {
				return Integer.parseUnsignedInt(value);
			} catch (NumberFormatException e) {
			}
		}
		return -1;
	}

	static String userName() {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				return SystemReader.getInstance()
						.getProperty(Constants.OS_USER_NAME_KEY);
			}
		});
	}

	private static class HostEntry implements ConfigRepository.Config {

		/**
		 * "Host name" of the HostEntry for the default options before the first
		 * host block in a config file.
		 */

		private static final Map<String, String> KEY_MAP = new HashMap<>();

		static {
			KEY_MAP.put("kex", "KexAlgorithms"); //$NON-NLS-1$//$NON-NLS-2$
			KEY_MAP.put("server_host_key", "HostKeyAlgorithms"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("cipher.c2s", "Ciphers"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("cipher.s2c", "Ciphers"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("mac.c2s", "Macs"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("mac.s2c", "Macs"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("compression.s2c", "Compression"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("compression.c2s", "Compression"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("compression_level", "CompressionLevel"); //$NON-NLS-1$ //$NON-NLS-2$
			KEY_MAP.put("MaxAuthTries", "NumberOfPasswordPrompts"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		/**
		 * Keys that can be specified multiple times, building up a list. (I.e.,
		 * those are the keys that do not follow the general rule of "first
		 * occurrence wins".)
		 */
		private static final Set<String> MULTI_KEYS = new HashSet<>();

		static {
		}

		/**
		 * Keys that take a whitespace-separated list of elements as argument.
		 * Because the dequote-handling is different, we must handle those in
		 * the parser. There are a few other keys that take comma-separated
		 * lists as arguments, but for the parser those are single arguments
		 * that must be quoted if they contain whitespace, and taking them apart
		 * is the responsibility of the user of those keys.
		 */
		private static final Set<String> LIST_KEYS = new HashSet<>();

		static {
		}

		private Map<String, String> options;

		private Map<String, List<String>> multiOptions;

		private Map<String, List<String>> listOptions;

		@Override
		public String getHostname() {
		}

		@Override
		public String getUser() {
		}

		@Override
		public int getPort() {
		}

		private static String mapKey(String key) {
			String k = KEY_MAP.get(key);
			if (k == null) {
				k = key;
			}
			return k.toUpperCase(Locale.ROOT);
		}

		private String findValue(String key) {
			String k = mapKey(key);
			String result = options != null ? options.get(k) : null;
			if (result == null) {
				List<String> values = listOptions != null ? listOptions.get(k)
						: null;
				if (values == null) {
					values = multiOptions != null ? multiOptions.get(k) : null;
				}
				if (values != null && !values.isEmpty()) {
					result = values.get(0);
				}
			}
			return result;
		}

		@Override
		public String getValue(String key) {
				String foo = findValue(key);
					return "none,zlib@openssh.com,zlib"; //$NON-NLS-1$
				}
				return "zlib@openssh.com,zlib,none"; //$NON-NLS-1$
			}
			return findValue(key);
		}

		@Override
		public String[] getValues(String key) {
			String k = mapKey(key);
			List<String> values = listOptions != null ? listOptions.get(k)
					: null;
			if (values == null) {
				values = multiOptions != null ? multiOptions.get(k) : null;
			}
			if (values == null || values.isEmpty()) {
				return new String[0];
			}
			return values.toArray(new String[0]);
		}

		public void setValue(String key, String value) {
			String k = key.toUpperCase(Locale.ROOT);
			if (MULTI_KEYS.contains(k)) {
				if (multiOptions == null) {
					multiOptions = new HashMap<>();
				}
				List<String> values = multiOptions.get(k);
				if (values == null) {
					values = new ArrayList<>(4);
					multiOptions.put(k, values);
				}
				values.add(value);
			} else {
				if (options == null) {
					options = new HashMap<>();
				}
				if (!options.containsKey(k)) {
					options.put(k, value);
				}
			}
		}

		public void setValue(String key, List<String> values) {
			if (values.isEmpty()) {
				return;
			}
			String k = key.toUpperCase(Locale.ROOT);
			if (MULTI_KEYS.contains(k)) {
				if (multiOptions == null) {
					multiOptions = new HashMap<>(2 * MULTI_KEYS.size());
				}
				List<String> items = multiOptions.get(k);
				if (items == null) {
					items = new ArrayList<>(values);
					multiOptions.put(k, items);
				} else {
					items.addAll(values);
				}
			} else {
				if (listOptions == null) {
					listOptions = new HashMap<>(2 * LIST_KEYS.size());
				}
				if (!listOptions.containsKey(k)) {
					listOptions.put(k, values);
				}
			}
		}

		public static boolean isListKey(String key) {
			return LIST_KEYS.contains(key.toUpperCase(Locale.ROOT));
		}

		/**
		 * Splits the argument into a list of whitespace-separated elements.
		 * Elements containing whitespace must be quoted and will be de-quoted.
		 *
		 * @param argument
		 *            argument part of the configuration line as read from the
		 *            config file
		 * @return a {@link List} of elements, possibly empty and possibly
		 *         containing empty elements
		 */
		public static List<String> parseList(String argument) {
			List<String> result = new ArrayList<>(4);
			int start = 0;
			int length = argument.length();
			while (start < length) {
				if (Character.isSpaceChar(argument.charAt(start))) {
					start++;
					continue;
				}
				if (argument.charAt(start) == '"') {
					int stop = argument.indexOf('"', ++start);
					if (stop < start) {
						break;
					}
					result.add(argument.substring(start, stop));
					start = stop + 1;
				} else {
					int stop = start + 1;
					while (stop < length
							&& !Character.isSpaceChar(argument.charAt(stop))) {
						stop++;
					}
					result.add(argument.substring(start, stop));
					start = stop + 1;
				}
			}
			return result;
		}

		protected void merge(HostEntry entry) {
			if (entry == null) {
				return;
			}
			if (entry.options != null) {
				if (options == null) {
					options = new HashMap<>();
				}
				for (Map.Entry<String, String> item : entry.options
						.entrySet()) {
					if (!options.containsKey(item.getKey())) {
						options.put(item.getKey(), item.getValue());
					}
				}
			}
			if (entry.listOptions != null) {
				if (listOptions == null) {
					listOptions = new HashMap<>(2 * LIST_KEYS.size());
				}
				for (Map.Entry<String, List<String>> item : entry.listOptions
						.entrySet()) {
					if (!listOptions.containsKey(item.getKey())) {
						listOptions.put(item.getKey(), item.getValue());
					}
				}

			}
			if (entry.multiOptions != null) {
				if (multiOptions == null) {
					multiOptions = new HashMap<>(2 * MULTI_KEYS.size());
				}
				for (Map.Entry<String, List<String>> item : entry.multiOptions
						.entrySet()) {
					List<String> values = multiOptions.get(item.getKey());
					if (values == null) {
						values = new ArrayList<>(item.getValue());
						multiOptions.put(item.getKey(), values);
					} else {
						values.addAll(item.getValue());
					}
				}
			}
		}

		private class Replacer {
			private final Map<Character, String> replacements = new HashMap<>();

			public Replacer(String originalHostName, File home) {
				replacements.put(Character.valueOf('%'), "%"); //$NON-NLS-1$
				replacements.put(Character.valueOf('d'), home.getPath());
				replacements.put(Character.valueOf('h'), originalHostName);
				if (host != null && host.indexOf('%') >= 0) {
					host = substitute(host, "h"); //$NON-NLS-1$
					options.put("HOSTNAME", host); //$NON-NLS-1$
				}
				if (host != null) {
					replacements.put(Character.valueOf('h'), host);
				}
				String localhost = SystemReader.getInstance().getHostname();
				replacements.put(Character.valueOf('l'), localhost);
				int period = localhost.indexOf('.');
				if (period > 0) {
					localhost = localhost.substring(0, period);
				}
				replacements.put(Character.valueOf('L'), localhost);
				replacements.put(Character.valueOf('n'), originalHostName);
				replacements.put(Character.valueOf('p'), getValue("PORT")); //$NON-NLS-1$
				replacements.put(Character.valueOf('r'), getValue("USER")); //$NON-NLS-1$
				replacements.put(Character.valueOf('u'), userName());
				replacements.put(Character.valueOf('C'),
						substitute("%l%h%p%r", "hlpr")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			public String substitute(String input, String allowed) {
				if (input == null || input.length() <= 1
						|| input.indexOf('%') < 0) {
					return input;
				}
				StringBuilder builder = new StringBuilder();
				int start = 0;
				int length = input.length();
				while (start < length) {
					int percent = input.indexOf('%', start);
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
						builder.append(input.substring(start, percent + 2));
					} else {
						builder.append(input.substring(start, percent))
								.append(replacement);
					}
					start = percent + 2;
				}
				return builder.toString();
			}
		}

		private List<String> substitute(List<String> values, String allowed,
				Replacer r) {
			List<String> result = new ArrayList<>(values.size());
			for (String value : values) {
				result.add(r.substitute(value, allowed));
			}
			return result;
		}

		private List<String> replaceTilde(List<String> values, File home) {
			List<String> result = new ArrayList<>(values.size());
			for (String value : values) {
				result.add(toFile(value, home).getPath());
			}
			return result;
		}

		protected void substitute(String originalHostName, File home) {
			Replacer r = new Replacer(originalHostName, home);
			if (multiOptions != null) {
				if (values != null) {
					values = substitute(values, "dhlru", r); //$NON-NLS-1$
					values = replaceTilde(values, home);
					multiOptions.put("IDENTITYFILE", values); //$NON-NLS-1$
				}
				if (values != null) {
					values = substitute(values, "dhlru", r); //$NON-NLS-1$
					values = replaceTilde(values, home);
					multiOptions.put("CERTIFICATEFILE", values); //$NON-NLS-1$
				}
			}
			if (listOptions != null) {
				if (values != null) {
					values = replaceTilde(values, home);
					listOptions.put("GLOBALKNOWNHOSTSFILE", values); //$NON-NLS-1$
				}
				if (values != null) {
					values = replaceTilde(values, home);
					listOptions.put("USERKNOWNHOSTSFILE", values); //$NON-NLS-1$
				}
			}
			if (options != null) {
				if (value != null) {
					value = r.substitute(value, "dhlru"); //$NON-NLS-1$
					value = toFile(value, home).getPath();
					options.put("IDENTITYAGENT", value); //$NON-NLS-1$
				}
			}
		}

		@Override
		@SuppressWarnings("nls")
		public String toString() {
			return "HostEntry [options=" + options + ", multiOptions="
					+ multiOptions + ", listOptions=" + listOptions + "]";
		}
