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
