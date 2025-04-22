		private static String getConfigKey(FileStore s) {
			final String storeKey;
			if (SystemReader.getInstance().isWindows()) {
				Object attribute = null;
				try {
				} catch (IOException ignored) {
				}
				if (attribute instanceof Integer) {
					storeKey = attribute.toString();
				} else {
					storeKey = s.name();
				}
			} else {
				storeKey = s.name();
			}
			return javaVersionPrefix + storeKey;
		}

		private static TimeUnit getUnit(long nanos) {
			TimeUnit unit;
			if (nanos < 200_000L) {
				unit = TimeUnit.NANOSECONDS;
			} else if (nanos < 200_000_000L) {
				unit = TimeUnit.MICROSECONDS;
			} else {
				unit = TimeUnit.MILLISECONDS;
			}
			return unit;
		}

