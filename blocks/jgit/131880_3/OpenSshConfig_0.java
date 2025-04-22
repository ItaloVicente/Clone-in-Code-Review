		private static final Map<String
				String.CASE_INSENSITIVE_ORDER);

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
					SshConstants.NUMBER_OF_PASSWORD_PROMPTS);
		}

		private static String mapKey(String key) {
			String k = KEY_MAP.get(key);
			return k != null ? k : key;
		}

