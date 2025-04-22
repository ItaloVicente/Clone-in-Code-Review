	private static class HostEntry implements ConfigRepository.Config {


		private static final Map<String

		static {
			KEY_MAP.put("kex"
			KEY_MAP.put("server_host_key"
			KEY_MAP.put("cipher.c2s"
			KEY_MAP.put("cipher.s2c"
			KEY_MAP.put("mac.c2s"
			KEY_MAP.put("mac.s2c"
			KEY_MAP.put("compression.s2c"
			KEY_MAP.put("compression.c2s"
			KEY_MAP.put("compression_level"
			KEY_MAP.put("MaxAuthTries"
		}

		private static final Set<String> MULTI_KEYS = new HashSet<>();

		static {
		}

		private static final Set<String> LIST_KEYS = new HashSet<>();

		static {
		}

		private Map<String

		private Map<String

		private Map<String

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
					return "none
				}
				return "zlib@openssh.com
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
			return values.toArray(new String[values.size()]);
		}

		public void setValue(String key
			String k = key.toUpperCase(Locale.ROOT);
			if (MULTI_KEYS.contains(k)) {
				if (multiOptions == null) {
					multiOptions = new HashMap<>();
				}
				List<String> values = multiOptions.get(k);
				if (values == null) {
					values = new ArrayList<>(4);
					multiOptions.put(k
				}
				values.add(value);
			} else {
				if (options == null) {
					options = new HashMap<>();
				}
				if (!options.containsKey(k)) {
					options.put(k
				}
			}
		}

		public void setValue(String key
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
					multiOptions.put(k
				} else {
					items.addAll(values);
				}
			} else {
				if (listOptions == null) {
					listOptions = new HashMap<>(2 * LIST_KEYS.size());
				}
				if (!listOptions.containsKey(k)) {
					listOptions.put(k
				}
			}
		}

		public static boolean isListKey(String key) {
			return LIST_KEYS.contains(key.toUpperCase(Locale.ROOT));
		}

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
					int stop = argument.indexOf('"'
					if (stop <= start) {
						break;
					}
					result.add(argument.substring(start + 1
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

		protected void merge(HostEntry entry) {
			if (entry == null) {
				return;
			}
			if (entry.options != null) {
				if (options == null) {
					options = new HashMap<>();
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
					listOptions = new HashMap<>(2 * LIST_KEYS.size());
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
					multiOptions = new HashMap<>(2 * MULTI_KEYS.size());
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
	}

