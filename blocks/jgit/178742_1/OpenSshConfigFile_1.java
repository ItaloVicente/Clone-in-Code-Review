				char ch = input.charAt(start);
				switch (ch) {
				case '%':
					if (start + 1 >= length) {
						break;
					}
					String replacement = null;
					ch = input.charAt(start + 1);
					if (ch == '%' || allowed.indexOf(ch) >= 0) {
						replacement = replacements.get(Character.valueOf(ch));
					}
					if (replacement == null) {
						builder.append('%').append(ch);
					} else {
						builder.append(replacement);
					}
					start += 2;
					continue;
				case '$':
					if (!withEnv || start + 2 >= length) {
						break;
					}
					ch = input.charAt(start + 1);
					if (ch == '{') {
						int close = input.indexOf('}'
						if (close > start + 2) {
							String variable = SystemReader.getInstance()
									.getenv(input.substring(start + 2
							if (!StringUtils.isEmptyOrNull(variable)) {
								builder.append(variable);
							}
							start = close + 1;
							continue;
						}
					}
					ch = '$';
					break;
				default:
