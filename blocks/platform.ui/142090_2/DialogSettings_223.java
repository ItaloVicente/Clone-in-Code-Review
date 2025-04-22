			printTag(name, parameters, true, newLine, false);
		}

		private static void appendEscapedChar(StringBuilder buffer, char c) {
			String replacement = getReplacement(c);
			if (replacement != null) {
				buffer.append('&');
				buffer.append(replacement);
				buffer.append(';');
			} else {
				buffer.append(c);
			}
		}

		private static String getEscaped(String s) {
			StringBuilder result = new StringBuilder(s.length() + 10);
			for (int i = 0; i < s.length(); ++i) {
