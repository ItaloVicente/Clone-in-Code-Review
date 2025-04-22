
	static class FsckKeyNameHolder {
		private static final Map<String

		static {
			errors = new HashMap<>();
			for (ObjectChecker.ErrorType m : ObjectChecker.ErrorType.values()) {
				errors.put(keyNameFor(m.name())
			}
		}

		@Nullable
		static ObjectChecker.ErrorType parse(String key) {
			return errors.get(toLowerCase(key));
		}

		private static String keyNameFor(String name) {
			StringBuilder r = new StringBuilder(name.length());
			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				if (c != '_') {
					r.append(c);
				}
			}
			return toLowerCase(r.toString());
		}

		private FsckKeyNameHolder() {
		}
	}
