
		private class Replacer {
			private final Map<Character

			public Replacer(String originalHostName
				replacements.put(Character.valueOf('%')
				replacements.put(Character.valueOf('d')
				replacements.put(Character.valueOf('h')
				if (host != null && host.indexOf('%') >= 0) {
					host = substitute(host
					options.put("HOSTNAME"
				}
				if (host != null) {
					replacements.put(Character.valueOf('h')
				}
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

		private List<String> substitute(List<String> values
				Replacer r) {
			List<String> result = new ArrayList<>(values.size());
			for (String value : values) {
				result.add(r.substitute(value
			}
			return result;
		}

		protected void substitute(String originalHostName
			Replacer r = new Replacer(originalHostName
			if (multiOptions != null) {
				if (values != null) {
					values = substitute(values
					multiOptions.put("IDENTITYFILE"
				}
				if (values != null) {
					values = substitute(values
					multiOptions.put("CERTIFICATEFILE"
				}
			}
			if (options != null) {
				if (value != null) {
					value = r.substitute(value
					options.put("IDENTITYAGENT"
				}
			}
		}
